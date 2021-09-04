package com.topjava.graduation.restaurant.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User extends AbstractBaseEntity {
    @NotBlank
    @Length(min = 4, max = 25)
    private String username;
    @NotEmpty
    @Email
    private String email;
    @NotBlank
    @Length(min = 8, max = 60)
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vote> votes;

    public void addVote(Vote vote) {
        this.votes.add(vote);
        vote.setUser(this);
    }

    public void removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setUser(null);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.roles = Stream.of(Role.USER)
                .collect(Collectors.toSet());
    }

    public User(Integer id, String username, String email, String password,
                boolean enabled, Set<Role> roles, List<Vote> votes) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.votes = votes;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return super.toString() +
                " User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}

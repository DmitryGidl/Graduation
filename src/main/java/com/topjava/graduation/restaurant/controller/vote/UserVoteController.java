package com.topjava.graduation.restaurant.controller.vote;

import com.topjava.graduation.restaurant.dto.VoteCreationDTO;
import com.topjava.graduation.restaurant.dto.VoteResponseDTO;
import com.topjava.graduation.restaurant.security.AuthenticatedUser;
import com.topjava.graduation.restaurant.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.topjava.graduation.restaurant.util.ExceptionUtil.throwExceptionIfBindingResultHasErrors;

@RestController
@RequestMapping("/vote")
public class UserVoteController {
    VoteService voteService;

    @Autowired
    public UserVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public VoteResponseDTO getCurrentVote(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return voteService.getCurrentUserVote(authenticatedUser.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponseDTO create(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                  @RequestBody @Valid VoteCreationDTO voteCreationDTO,
                                  BindingResult bindingResult) {
        throwExceptionIfBindingResultHasErrors(bindingResult);
        return voteService.addVote(authenticatedUser.getUser(), voteCreationDTO);
    }

    @PutMapping
    public VoteResponseDTO update(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                  @RequestBody @Valid VoteCreationDTO voteCreationDTO,
                                  BindingResult bindingResult) {
        throwExceptionIfBindingResultHasErrors(bindingResult);
        return voteService.updateCurrentUserVote(authenticatedUser.getUser(), voteCreationDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentVote(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        voteService.deleteCurrentUserVote(authenticatedUser.getId());
    }

    @GetMapping("/history")
    public List<VoteResponseDTO> getAllCurrentUserHistory(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return voteService.getUserVoteHistory(authenticatedUser.getId());
    }

    @GetMapping("/history/{id}")
    public VoteResponseDTO getOneCurrentUserHistory(@PathVariable int id, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return voteService.getOneVoteCurrentUser(authenticatedUser.getId(), id);
    }
}

package com.topjava.graduation.restaurant.controller.vote;

import com.topjava.graduation.restaurant.dto.VoteCreationDTO;
import com.topjava.graduation.restaurant.dto.VoteResponseDTO;
import com.topjava.graduation.restaurant.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.topjava.graduation.restaurant.util.ExceptionUtil.throwExceptionIfBindingResultHasErrors;

@RestController
@RequestMapping("/admin/votes")
public class AdminVoteController {
    VoteService voteService;

    @Autowired
    public AdminVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public List<VoteResponseDTO> getAllVotesToday() {
        return voteService.getAllToday();
    }

    @GetMapping("/{id}")
    public VoteResponseDTO getAnyVote(@PathVariable int id) {
        return voteService.getByid(id);
    }

    @PutMapping("/{id}")
    public VoteResponseDTO updateAnyVote(@PathVariable int id, @RequestBody @Valid VoteCreationDTO voteCreationDTO,
                                         BindingResult bindingResult) {
        throwExceptionIfBindingResultHasErrors(bindingResult);
        return voteService.update(id, voteCreationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnyVote(@PathVariable int id) {
        voteService.deleteById(id);
    }

    @GetMapping("/history")
    public List<VoteResponseDTO> getAllUsersVoteHistory() {
        return voteService.getAllHistory();
    }

    @GetMapping("/history/users/{id}")
    public List<VoteResponseDTO> getUserVoteHistory(@PathVariable int id) {
        return voteService.getUserVoteHistory(id);
    }
}

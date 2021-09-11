package com.topjava.graduation.restaurant.controller.vote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topjava.graduation.restaurant.ControllerTestConfig;
import com.topjava.graduation.restaurant.service.UserService;
import com.topjava.graduation.restaurant.service.VoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static com.topjava.graduation.restaurant.test_data.UserTestModels.USER_USERNAME;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.getBasicUser;
import static com.topjava.graduation.restaurant.test_data.VoteTestModel.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserVoteController.class)
@Import(ControllerTestConfig.class)
@WithUserDetails(USER_USERNAME)
public class UserVoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    VoteService voteService;
    @MockBean
    UserService userService;

    @Test
    @WithUserDetails(USER_USERNAME)
    void createVote() throws Exception {
        var voteCreationAdriano = getVoteCreationAdriano();
        Mockito.when(voteService.addVote(getBasicUser(), voteCreationAdriano)).thenReturn(getVoteResponseAdriano());

        var mockRequest = post(
                "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voteCreationAdriano));

        mockMvc.perform(mockRequest)
                .andExpect(jsonPath("$.restaurantName", is("Adriano's pizza")));
    }

    @Test
    void deleteCurrentUserVote() throws Exception {

        mockMvc.perform(delete("/votes"));

        Mockito.verify(voteService, times(1))
                .deleteCurrentUserVote(122);
    }

    @Test
    void getAllCurrentUserVoteHistory() throws Exception {
        Mockito.when(voteService.getUserVoteHistory(122)).thenReturn(getResponseVotes());

        var mockRequest = get("/votes/history");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].restaurantAddress", is("72 Mill Lane")));
    }

    @Test
    void getOneCurrentUserVote() throws Exception {
        Mockito.when(voteService.getOneVoteCurrentUser(122, 5)).thenReturn(getVoteResponseAdriano());

        var mockRequest = get("/votes/history/5");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.restaurantName", is("Adriano's pizza")));
    }
}


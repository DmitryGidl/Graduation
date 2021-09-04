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

import static com.topjava.graduation.restaurant.test_data.UserTestModels.ADMIN_USERNAME;
import static com.topjava.graduation.restaurant.test_data.VoteTestModel.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminVoteController.class)
@Import(ControllerTestConfig.class)
@WithUserDetails(ADMIN_USERNAME)
 class AdminVoteControllerTest {

    ObjectMapper objectMapper;
    MockMvc mockMvc;

    @MockBean
    VoteService voteService;
    @MockBean
    UserService userService;

    @Autowired
    public AdminVoteControllerTest(ObjectMapper objectMapper, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    @Test
    void getAllVotesToday() throws Exception {
        Mockito.when(voteService.getAllToday()).thenReturn(getResponseVotes());
        var mockRequest = get("/admin/vote");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].restaurantAddress", is("проспект Победы, 9Б")));
    }

    @Test
    void getAnyVote() throws Exception {
        Mockito.when(voteService.getByid(52)).thenReturn(getVoteResponseDominos());

        var mockRequest = get("/admin/vote/52");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.restaurantName", is("Dominos Pizza")));
    }

    @Test
    void UpdateAnyVote() throws Exception {
        var voteCreationMamamia = getVoteCreationMamamia();
        Mockito.when(voteService.update(5, voteCreationMamamia)).thenReturn(getVoteResponseMamamia());

        var mockRequest = put("/admin/vote/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voteCreationMamamia));

        mockMvc.perform(mockRequest)
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.restaurantName", is("Mamamia")));
    }

    @Test
    void deleteAnyVote() throws Exception {
        mockMvc.perform(delete("/admin/vote/23"));

        Mockito.verify(voteService, times(1)).deleteById(23);

    }

    @Test
    void getVoteHistory() throws Exception {
        Mockito.when(voteService.getAllHistory()).thenReturn(getResponseVotes());

        var mockRequest = get("/admin/vote/history");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].restaurantAddress", is("проспект Победы, 9Б")));

    }

    @Test
    void getUserVoteHistory() throws Exception {

        Mockito.when(voteService.getUserVoteHistory(15)).thenReturn(getResponseVotes());

        var mockRequest = get("/admin/vote/history/user/15");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].restaurantAddress", is("Басейна, 17")));
    }
}


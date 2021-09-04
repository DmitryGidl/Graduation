package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.dto.VoteResponseDTO;
import com.topjava.graduation.restaurant.entity.Vote;
import com.topjava.graduation.restaurant.exception.EntityNotFoundException;
import com.topjava.graduation.restaurant.exception.LateToVoteException;
import com.topjava.graduation.restaurant.repository.RestaurantRepository;
import com.topjava.graduation.restaurant.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.Optional;

import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.getRestaurantDominos;
import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.getRestaurantBangalore;
import static com.topjava.graduation.restaurant.test_data.TestBaseData.todayDate;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.getBasicUser;
import static com.topjava.graduation.restaurant.test_data.VoteTestModel.*;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;
    @Mock
    RestaurantRepository restaurantRepository;
    @Spy
    Clock clock = Clock.fixed(fixedInstant, ZONE);
    @Captor
    ArgumentCaptor<Vote> voteCaptor;

    private static final ZoneId ZONE = ZoneId.of("Europe/Kiev");
    private static final LocalDate TODAY = LocalDate.now(ZONE);
    private static final LocalTime TIME = LocalTime.of(10, 0);
    private static final Instant fixedInstant
            = TODAY.atTime(TIME).atZone(ZONE).toInstant();

    @InjectMocks
    VoteService voteService;

    @Test
    void addVote_timeValidAndVotePresent_updateVote() {
        Mockito.when(restaurantRepository.findById(1)).thenReturn(Optional.of(getRestaurantDominos()));
        var oldVote = getVoteBangalore();
        var voteServiceSpy = Mockito.spy(voteService);
        var voteCreationDominos = getVoteCreationDominos();
        Mockito.when(voteRepository.findByUserIdAndVoteDate(122, LocalDate.now(clock))).thenReturn(Optional.of(oldVote));
        Mockito.doReturn(getVoteDominos()).when(voteServiceSpy).updateLogic(oldVote, voteCreationDominos);

       VoteResponseDTO actualResponse = voteServiceSpy.addVote(getBasicUser(), voteCreationDominos);

       VoteResponseDTO expectedResponse = getVoteResponseDominos();
       assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void addVote_timeValidAndVoteAbsent_saveVote() {
        var restaurantDominos = getRestaurantDominos();
        var user = getBasicUser();
        var creationDominos = getVoteCreationDominos();
Mockito.when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurantDominos));
Mockito.when(voteRepository.findByUserIdAndVoteDate(122, LocalDate.now(clock))).thenReturn(Optional.empty());
var voteTosave = new Vote(0, restaurantDominos, todayDate, user);
Mockito.when(voteRepository.save(voteCaptor.capture())).thenReturn(voteTosave);

var actualVoteResponse = voteService.addVote(user,creationDominos);

var expectedVoteResponse = new VoteResponseDTO(0, "User", "Dominos Pizza",
        "30 Queen Street", 1, todayDate);
assertEquals(expectedVoteResponse, actualVoteResponse);
assertTrue(reflectionEquals(voteTosave, voteCaptor.getValue()));

    }

    @Test
    void addVote_timeInvalid_throwException() {
        final LocalTime invalidTime = LocalTime.of(11, 15);

        Mockito.when(clock.instant()).thenReturn(TODAY.atTime(invalidTime).atZone(ZONE).toInstant());
        var basicUser = getBasicUser();
        var voteCreationAdriano = getVoteCreationAdriano();
        assertThrows(LateToVoteException.class, () -> voteService.addVote(basicUser, voteCreationAdriano));
    }

    @Test
    void updateCurrentUserVote_votePresent_success() {
        var user = getBasicUser();
        var voteCreationBangalore = getVoteCreationBangalore();
        var updatedVoteBangalore = getVoteBangalore();
        var oldVoteAdriano = getVoteAdriano();
        var voteServiceSpy = Mockito.spy(voteService);
        Mockito.when(voteRepository.findByUserIdAndVoteDate(122, LocalDate.now(clock)))
                .thenReturn(Optional.of(oldVoteAdriano));
        Mockito.doReturn(updatedVoteBangalore).when(voteServiceSpy).updateLogic(oldVoteAdriano, voteCreationBangalore);


        var actualResponseVote = voteServiceSpy.updateCurrentUserVote(user, voteCreationBangalore);

        var expectedResponseVote = new VoteResponseDTO(2, "User", "Bangalore Spices",
                "87 Stanley Road", 2, todayDate);
        assertEquals(expectedResponseVote, actualResponseVote);
    }

    @Test
    void updateCurrentUserVote_voteAbsent_throwException() {
        var voteCreationBangalore = getVoteCreationBangalore();
        var user = getBasicUser();
        Mockito.when(voteRepository.findByUserIdAndVoteDate(122, LocalDate.now(clock))).thenReturn(Optional.empty());

       assertThrows(EntityNotFoundException.class, () -> voteService.updateCurrentUserVote(user, voteCreationBangalore));
    }

    @Test
    void updateCurrentUserVote_timeInvalid_throwException() {
        final LocalTime invalidTime = LocalTime.of(15, 27);
        Mockito.when(clock.instant()).thenReturn(TODAY.atTime(invalidTime).atZone(ZONE).toInstant());
        var user = getBasicUser();
        var voteCreationAdriano = getVoteCreationAdriano();

        assertThrows(LateToVoteException.class, () -> voteService.updateCurrentUserVote(user, voteCreationAdriano));
    }

    @Test
    void updateLogic_restaurantPresent_success() {
        Mockito.when(restaurantRepository.findById(2)).thenReturn(Optional.of(getRestaurantBangalore()));
        var voteToSave = new Vote(3, getRestaurantBangalore(), todayDate, getBasicUser());
        Mockito.when(voteRepository.save(voteCaptor.capture())).thenReturn(voteToSave);

        var actualResponse = voteService.updateLogic(getVoteAdriano(), getVoteCreationBangalore());

        assertTrue(reflectionEquals(voteToSave, voteCaptor.getValue()));
        var expectedResponse = new Vote(3, getRestaurantBangalore(), todayDate, getBasicUser());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateLogic_restaurantAbsent_throwException() {
        var oldVoteAdriano = getVoteAdriano();
        var newVoteCreationBangalore = getVoteCreationBangalore();
        Mockito.when(restaurantRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> voteService.
                updateLogic(oldVoteAdriano, newVoteCreationBangalore));

    }

    @Test
    void getAllHistory_returnVoteList() {
        Mockito.when(voteRepository.findAll()).thenReturn(getVotes());
        var actualResponseList = voteService.getAllHistory();
        var expectedResponseList = getResponseVotes();
        assertEquals(expectedResponseList, actualResponseList);
    }

    @Test
    void getAllToday_returnVoteList() {
        Mockito.when(voteRepository.getByVoteDate(todayDate)).thenReturn(getVotes());
        var actualResponseList = voteService.getAllToday();
        var expectedResponseList = getResponseVotes();
        assertEquals(expectedResponseList, actualResponseList);
    }

    @Test
    void getUserVoteHistory_returnVoteList() {
        Mockito.when(voteRepository.getByUserId(13)).thenReturn(getVotes());
        var actualResponseList = voteService.getUserVoteHistory(13);
        var expectedResponseList = getResponseVotes();
        assertEquals(expectedResponseList, actualResponseList);
    }

    @Test
    void getById_votePresent_returnVote() {
        Mockito.when(voteRepository.findById(15)).thenReturn(Optional.of(getVoteBangalore()));
        var actualResponseVote = voteService.getByid(15);
        var expectedResponseVote = getVoteResponseBangalore();
        assertEquals(expectedResponseVote, actualResponseVote);
    }

    @Test
    void deleteById_votePresent_success() {
        Mockito.when(voteRepository.deleteVoteById(15)).thenReturn(1);
        assertDoesNotThrow(() -> voteService.deleteById(15));
    }

    @Test
    void deleteById_voteAbsent_throwException() {
        Mockito.when(voteRepository.deleteVoteById(13)).thenReturn(0);
        assertThrows(EntityNotFoundException.class, () -> voteService.deleteById(13));
    }

    @Test
    void deleteCurrentUserVote_votePresent_success() {
        Mockito.when(voteRepository.deleteVoteByUserId(13)).thenReturn(1);
        assertDoesNotThrow(() -> voteService.deleteCurrentUserVote(13));
    }

    @Test
    void deleteCurrentUserVote_voteAbsent_throwException() {
        Mockito.when(voteRepository.deleteVoteByUserId(13)).thenReturn(0);
        assertThrows(EntityNotFoundException.class, () -> voteService.deleteCurrentUserVote(13));
    }

    @Test
    void update_votePresent_success() {
        Mockito.when(voteRepository.findById(3)).thenReturn(Optional.of(getVoteAdriano()));
        Mockito.when(restaurantRepository.findById(1)).thenReturn(Optional.of(getRestaurantDominos()));

        var resultVoteResponse = voteService.update(3, getVoteCreationDominos());

        var expectedVoteResponse = new VoteResponseDTO(3, "User",
                "Dominos Pizza", "30 Queen Street", 1, todayDate);
        assertEquals(expectedVoteResponse, resultVoteResponse);
        var expectedVoteSave = new Vote(3, getRestaurantDominos(), todayDate, getBasicUser());
        Mockito.verify(voteRepository, times(1)).save(voteCaptor.capture());
        assertTrue(reflectionEquals(expectedVoteSave, voteCaptor.getValue()));
    }

    @Test
    void update_voteAbsent_throwException() {
        Mockito.when(voteRepository.findById(15)).thenReturn(Optional.of(getVoteAdriano()));
        Mockito.when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

        var voteCreationDominos = getVoteCreationDominos();

        assertThrows(EntityNotFoundException.class, () -> voteService.update(15, voteCreationDominos));
    }
    @Test
    void update_TimeInvalid_throwException() {
        final LocalTime invalidTime = LocalTime.of(15, 27);
        Mockito.when(clock.instant()).thenReturn(TODAY.atTime(invalidTime).atZone(ZONE).toInstant());
        var voteCreationDominos = getVoteCreationDominos();

        assertThrows(LateToVoteException.class, () -> voteService.update(15, voteCreationDominos));
    }

    @Test
    void getCurrentUserVote_votePresent_returnVote() {
        Mockito.when(voteRepository.getByUserIdAndVoteDate(25, todayDate)).thenReturn((getVoteAdriano()));

        var actualVoteResponse = voteService.getCurrentUserVote(25);

        var expectedVoteResponse = getVoteResponseAdriano();
        assertEquals(expectedVoteResponse, actualVoteResponse);
    }

    @Test
    void getCurrentUserVote_voteAbsent_returnNull() {
        Mockito.when(voteRepository.getByUserIdAndVoteDate(25, todayDate)).thenReturn(null);
        assertNull(voteService.getCurrentUserVote(25));
    }

    @Test
    void getOneVoteCurrentUser_votePresent_returnVote() {
        Mockito.when(voteRepository.findByUserIdAndId(25, 122)).thenReturn(Optional.of(getVoteBangalore()));
        assertEquals(getVoteResponseBangalore(), voteService.getOneVoteCurrentUser(25, 122));
    }

    @Test
    void getOneVoteCurrentUser_voteAbsent_throwException() {
        Mockito.when(voteRepository.findByUserIdAndId(25, 122)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> voteService.getOneVoteCurrentUser(25, 122));
    }

}


package com.interview.betgame.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.betgame.exception.BadRequestException;
import com.interview.betgame.repository.GameRepository;
import com.interview.betgame.repository.UserRepository;
import com.interview.betgame.service.GameService;
import com.interview.betgame.util.Card;
import com.interview.betgame.util.GuessResult;
import com.interview.betgame.util.Validator;
import com.interview.betgame.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Card> gameCards = new ArrayList<Card>();

    @Mock
    private Validator validator;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    @Before
    public void setUp() {

    }

    @Test
    public void testStartGame_returnsCard() throws JsonProcessingException {
        // given
        JsonNode requestBody = objectMapper.readTree(TestUtils.PAYLOAD_START_GAME_VALID);

        // when
        Card drawnCard = gameService.startGame(requestBody);

        // then
        assertNotNull(drawnCard);
    }

    @Test(expected = BadRequestException.class)
    public void testStartGame_throwsException() throws JsonProcessingException {
        // given
        JsonNode requestBody = objectMapper.readTree(TestUtils.PAYLOAD_START_GAME_INVALID);
        doThrow(BadRequestException.class).when(validator).validateRequestBodyStartGame(any());

        // when
        gameService.startGame(requestBody);
    }

    @Test
    public void testShuffleGame_returnsCard() throws JsonProcessingException {
        // given
        JsonNode requestBody = objectMapper.readTree(TestUtils.PAYLOAD_SHUFFLE_GAME_VALID);

        when(userRepository.findByName(any())).thenReturn(Optional.of(TestUtils.testUser));
        when(gameRepository.findByUserId(any())).thenReturn(TestUtils.testGame);

        // when
        Card drawnCard = gameService.shuffleGame(requestBody);

        // then
        assertNotNull(drawnCard);
    }

    @Test(expected = BadRequestException.class)
    public void testShuffleGame_throwsException() throws JsonProcessingException {
        // given
        JsonNode requestBody = objectMapper.readTree(TestUtils.PAYLOAD_SHUFFLE_GAME_INVALID);
        doThrow(BadRequestException.class).when(validator).validateRequestBodyShuffleGame(any());

        // when
        gameService.shuffleGame(requestBody);
    }

    @Test
    public void testBetHigher_returnsCard() throws JsonProcessingException {
        // given
        JsonNode requestBody = objectMapper.readTree(TestUtils.PAYLOAD_BET_HIGHER_VALID);
        when(userRepository.findByName(any())).thenReturn(Optional.of(TestUtils.testUser));

        TestUtils.drawFirstCardFromGame(TestUtils.testGame);
        when(gameRepository.findByUserId(any())).thenReturn(TestUtils.testGame);

        // when
        GuessResult guessResult = gameService.bet(requestBody);

        // then
        assertNotNull(guessResult);
        assertNotNull(guessResult.getCard());
        assertNotNull(guessResult.getHasGuessedRight());
    }

    @Test(expected = BadRequestException.class)
    public void testBetHigher_throwsException() throws JsonProcessingException {
        // given
        JsonNode requestBody = objectMapper.readTree(TestUtils.PAYLOAD_BET_INVALID);
        doThrow(BadRequestException.class).when(validator).validateRequestBodyBet(any());

        // when
        gameService.bet(requestBody);
    }
}

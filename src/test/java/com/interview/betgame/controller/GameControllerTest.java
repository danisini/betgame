package com.interview.betgame.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.interview.betgame.exception.BadRequestException;
import com.interview.betgame.service.GameService;
import com.interview.betgame.util.Card;
import com.interview.betgame.util.GuessResult;
import com.interview.betgame.util.Rank;
import com.interview.betgame.util.Suit;
import com.interview.betgame.utils.TestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    private static final short ONE = 1;
    @Mock
    private GameService gameService;
    @InjectMocks
    private GameController gameController;

    private GuessResult guessResult;
    private Card card;
    private HttpServletRequest request;
    private JsonNode body;

    @Before
    public void setUp() {
        card = new Card(ONE, Suit.CLUBS, Rank.TWO);
        guessResult = new GuessResult(card, ONE);

    }

    @Test
    public void testBet_success() throws JsonProcessingException {
        // given
        when(gameService.bet(any())).thenReturn(guessResult);

        // when
        ResponseEntity<GuessResult> response = gameController.bet(body);

        // then
        assertEquals(guessResult, response.getBody());
    }

    @Test(expected = BadRequestException.class)
    public void testBet_failure() throws JsonProcessingException {
        // given
        when(gameService.bet(any())).thenThrow(new BadRequestException(TestUtils.ERROR_MESSAGE));

        // when
        gameController.bet(body);
    }

    @Test
    public void testShuffleGame_success() throws JsonProcessingException {
        // given
        when(gameService.shuffleGame(any())).thenReturn(card);

        // when
        ResponseEntity<Card> response = gameController.shuffle(body);

        // then
        assertEquals(card, response.getBody());
    }

    @Test(expected = BadRequestException.class)
    public void testShuffleGame_failure() throws JsonProcessingException {
        // given
        when(gameService.shuffleGame(any())).thenThrow(new BadRequestException(TestUtils.ERROR_MESSAGE));

        // when
        gameController.shuffle(body);
    }

    @Test
    public void testStartGame_success() throws JsonProcessingException {
        // given
        when(gameService.startGame(any())).thenReturn(card);

        // when
        ResponseEntity<Card> response = gameController.start(body);

        // then
        assertEquals(card, response.getBody());
    }

    @Test(expected = BadRequestException.class)
    public void testStartGame_failure() throws JsonProcessingException {
        // given
        when(gameService.startGame(any())).thenThrow(new BadRequestException(TestUtils.ERROR_MESSAGE));

        // when
        gameController.start(body);
    }
}

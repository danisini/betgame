package com.interview.betgame.utils;

import com.interview.betgame.entity.Game;
import com.interview.betgame.entity.User;
import com.interview.betgame.util.Card;

import java.util.List;
import java.util.Stack;

public class TestUtils {
    public static final String ERROR_MESSAGE = "error message";
    public static final String PAYLOAD_START_GAME_VALID = "{\"userName\": \"Dani\", \"balance\": 10.5}";
    public static final String PAYLOAD_SHUFFLE_GAME_VALID = "{\"userName\": \"Dani\"}";
    public static final String PAYLOAD_BET_HIGHER_VALID = "{\"userName\": \"Dani\", \"bet\": \"higher\", \"betPrice\": 10.5}";
    public static final String PAYLOAD_START_GAME_INVALID = "{\"balance\": 10.5}";
    public static final String PAYLOAD_SHUFFLE_GAME_INVALID = "{\"user\": \"Dani\"}";
    public static final String PAYLOAD_BET_INVALID = "{\"userName\": \"Dani\", \"bet\": \"invalid\"}";

    public static User testUser = new User("Dani");
    public static Game testGame = new Game(10.5);

    public static void drawFirstCardFromGame(Game game) {
        List<Short> currentCardIds = game.getCurrentCardIds();
        Stack<Short> takenCardIds = game.getTakenCardIds();

        Short drawnCard = currentCardIds.get(0);
        currentCardIds.remove(0);

        takenCardIds.push(drawnCard);

        game.setCurrentCardIds(currentCardIds);
        game.setTakenCardIds(takenCardIds);
    }
}

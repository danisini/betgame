package com.interview.betgame.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.interview.betgame.util.Card;
import com.interview.betgame.util.GuessResult;

public interface GameService {
    Card startGame(JsonNode requestBody) throws JsonProcessingException;

    Card shuffleGame(JsonNode requestBody) throws JsonProcessingException;

    GuessResult bet(JsonNode requestBody) throws JsonProcessingException;
}

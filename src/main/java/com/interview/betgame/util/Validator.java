package com.interview.betgame.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.betgame.entity.User;
import com.interview.betgame.exception.BadRequestException;
import com.interview.betgame.exception.ConflictException;
import com.interview.betgame.exception.NotFoundException;
import com.interview.betgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class Validator {
    private final UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public Validator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateRequestBodyStartGame(JsonNode requestBody) throws JsonProcessingException {
        validateUserName(requestBody);

        if (!requestBody.hasNonNull(CommonConstants.BALANCE)) {
            throw new BadRequestException("Missing 'balance' in the payload!");
        }

        String userName = objectMapper.treeToValue(requestBody.get(CommonConstants.USER_NAME), String.class);
        Optional<User> foundUserInTheDB = userRepository.findByName(userName);

        if (foundUserInTheDB.isPresent()) {
            throw new ConflictException("This user has already started a game!");
        }
    }

    public void validateRequestBodyShuffleGame(JsonNode requestBody) throws JsonProcessingException {
        validateUserName(requestBody);

        validateUserExists(requestBody);
    }

    public void validateRequestBodyBet(JsonNode requestBody) throws JsonProcessingException {
        validateUserName(requestBody);

        validateUserExists(requestBody);

        if (!requestBody.hasNonNull(CommonConstants.BET)) {
            throw new BadRequestException("Missing 'bet' in the payload!");
        }
        String bet = objectMapper.treeToValue(requestBody.get(CommonConstants.BET), String.class);

        if (!CommonConstants.HIGHER.equals(bet) && !CommonConstants.LOWER.equals(bet)) {
            throw new BadRequestException("bet's value should be either 'higher' or 'lower'");
        }

        if (!requestBody.hasNonNull(CommonConstants.BET_PRICE)) {
            throw new BadRequestException("Missing 'betPrice' in the payload!");
        }

        double betPrice = objectMapper.treeToValue(requestBody.get(CommonConstants.BET_PRICE), Double.class);

        if (betPrice <= 0.0) {
            throw new BadRequestException("'betPrice' must be greater than 0");
        }
    }

    private void validateUserName(JsonNode requestBody) {
        if (!requestBody.hasNonNull(CommonConstants.USER_NAME)) {
            throw new BadRequestException("Missing 'userName' in the payload!");
        }
    }

    private void validateUserExists(JsonNode requestBody) throws JsonProcessingException {
        String userName = objectMapper.treeToValue(requestBody.get(CommonConstants.USER_NAME), String.class);
        Optional<User> foundUserInTheDB = userRepository.findByName(userName);

        if (foundUserInTheDB.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
    }


}

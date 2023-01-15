package com.interview.betgame.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.betgame.entity.Game;
import com.interview.betgame.entity.User;
import com.interview.betgame.exception.BadRequestException;
import com.interview.betgame.repository.GameRepository;
import com.interview.betgame.repository.UserRepository;
import com.interview.betgame.service.GameService;
import com.interview.betgame.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    private static final short ONE = 1;
    private static final short MINUS_ONE = -1;
    private final Validator validator;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public GameServiceImpl(Validator validator, UserRepository userRepository, GameRepository gameRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Card startGame(JsonNode requestBody) throws JsonProcessingException {
        validator.validateRequestBodyStartGame(requestBody);

        String userName = objectMapper.treeToValue(requestBody.get(CommonConstants.USER_NAME), String.class);
        double balance = objectMapper.treeToValue(requestBody.get(CommonConstants.BALANCE), Double.class);

        User user = new User(userName);
        userRepository.save(user);

        Game game = new Game(balance);
        game.setUserId(user.getId());

        return drawCard(game);
    }

    @Override
    public Card shuffleGame(JsonNode requestBody) throws JsonProcessingException {
        validator.validateRequestBodyShuffleGame(requestBody);

        String userName = objectMapper.treeToValue(requestBody.get(CommonConstants.USER_NAME), String.class);
        User user = userRepository.findByName(userName).get();

        Game game = gameRepository.findByUserId(user.getId());

        returnDrawnCards(game);
        return drawCard(game);
    }

    @Override
    public GuessResult bet(JsonNode requestBody) throws JsonProcessingException {
        validator.validateRequestBodyBet(requestBody);

        String userName = objectMapper.treeToValue(requestBody.get(CommonConstants.USER_NAME), String.class);
        User user = userRepository.findByName(userName).get();

        Game game = gameRepository.findByUserId(user.getId());

        String bet = objectMapper.treeToValue(requestBody.get(CommonConstants.BET), String.class);
        double betPrice = objectMapper.treeToValue(requestBody.get(CommonConstants.BET_PRICE), Double.class);
        checkIsBetPriceNotGreaterThanBalance(betPrice, game.getBalance());

        Card previousDrawnCard = getCardById(game.getTakenCardIds().peek());
        Card drawnCard = drawCard(game);

        short hasGuessedRight = hasGuessedRight(previousDrawnCard, drawnCard, bet);

        evaluateGuess(hasGuessedRight, game, betPrice);
        return new GuessResult(drawnCard, hasGuessedRight);
    }

    private short hasGuessedRight(Card previousDrawnCard, Card drawnCard, String bet) {
        if (CommonConstants.HIGHER.equals(bet) && previousDrawnCard.getRank().getValue() < drawnCard.getRank().getValue()) {
            return 1;
        }

        if (CommonConstants.LOWER.equals(bet) && previousDrawnCard.getRank().getValue() > drawnCard.getRank().getValue()) {
            return 1;
        }

        if (CommonConstants.HIGHER.equals(bet) && previousDrawnCard.getRank().getValue() > drawnCard.getRank().getValue()) {
            return -1;
        }

        if (CommonConstants.LOWER.equals(bet) && previousDrawnCard.getRank().getValue() < drawnCard.getRank().getValue()) {
            return -1;
        }

        return 0;
    }

    private void evaluateGuess(short hasGuessedRight, Game game, double betPrice) {
        if (hasGuessedRight == ONE) {
            game.setBalance(game.getBalance() + betPrice);
            gameRepository.save(game);
        } else if (hasGuessedRight == MINUS_ONE) {
            game.setBalance(game.getBalance() - betPrice);
            gameRepository.save(game);
        }
    }

    private void checkIsBetPriceNotGreaterThanBalance(double betPrice, double balance) {
        if (betPrice > balance) {
            throw new BadRequestException("'betPrice' is higher than balance. " +
                    "Your balance is "+ balance);
        }
    }

    private Card drawCard(Game game) {
        List<Short> gameCurrentCardIds = game.getCurrentCardIds();
        Stack<Short> gameTakenCardIds = game.getTakenCardIds();
        Random rand = new Random();

        int randomCardIndex = rand.nextInt(gameCurrentCardIds.size());
        short drawnCardId = gameCurrentCardIds.get(randomCardIndex);

        gameCurrentCardIds.remove(randomCardIndex);
        gameTakenCardIds.push(drawnCardId);

        gameRepository.save(game);

        return getCardById(drawnCardId);
    }

    private Card getCardById(short id) {
        short currentCardNumber = 1;

        Card card = null;
        for (Suit suit: Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (id == currentCardNumber) {
                    card = new Card(currentCardNumber, suit, rank);
                    return card;
                }

                currentCardNumber ++;
            }
        }

        return card;
    }

    private void returnDrawnCards(Game game) {
        List<Short> gameCurrentCardIds = game.getCurrentCardIds();
        Stack<Short> gameTakenCardIds = game.getTakenCardIds();

        while (!gameTakenCardIds.isEmpty()) {
            short cardId = gameTakenCardIds.peek();
            gameCurrentCardIds.add(cardId);
            gameTakenCardIds.pop();
        }

        game.setCurrentCardIds(gameCurrentCardIds);
        game.setTakenCardIds(gameTakenCardIds);

        gameRepository.save(game);
    }

}

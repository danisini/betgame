package com.interview.betgame.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.interview.betgame.util.*;
import com.interview.betgame.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CommonConstants.APP_VERSION)
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping("/bet")
    public ResponseEntity<GuessResult> bet(@RequestBody JsonNode requestBody) throws JsonProcessingException {
        //userDTO - name, balance

        //UserDTO userDTO = userService.getUser(request.getRemoteUser());

        GuessResult guessResult = gameService.bet(requestBody);

        return ResponseEntity.status(HttpStatus.OK).body(guessResult);
    }

    @PostMapping("/shuffle")
    public ResponseEntity<Card> shuffle(@RequestBody JsonNode requestBody) throws JsonProcessingException {

        //UserDTO userDTO = userService.getUser(request.getRemoteUser());

        Card drawnCard = gameService.shuffleGame(requestBody);

        return ResponseEntity.status(HttpStatus.OK).body(drawnCard);
    }

    @PostMapping("/start")
    public ResponseEntity<Card> start(@RequestBody JsonNode requestBody) throws JsonProcessingException {
        //body contains balance, userName
        // ostaveno za izsledvane kakvo ima v nego
        //UserDTO userDTO = userService.getUser(request.getRemoteUser());

        Card drawnCard = gameService.startGame(requestBody);

        return ResponseEntity.status(HttpStatus.OK).body(drawnCard);
    }

}

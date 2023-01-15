package com.interview.betgame.mapper;

import com.interview.betgame.dto.GameDTO;
import com.interview.betgame.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    public GameDTO toGameDTO(Game game) {
        if (game == null) {
            return null;
        }

        GameDTO gameDTO = new GameDTO();
        gameDTO.setBalance(game.getBalance());

        return gameDTO;
    }

    public Game toGameEntity(GameDTO gameDTO) {
        if(gameDTO == null) {
            return null;
        }
        Game game = new Game(gameDTO.getBalance());

        return game;
    }
}

package com.interview.betgame.mapper;

import com.interview.betgame.dto.GameDTO;
import com.interview.betgame.entity.Game;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GameMapperTest {
    private static final double BALANCE = 5.5;
    private static final String ERROR_DIFFERENT_BALANCE = "Game balance is different";
    private GameMapper gameMapper;

    @Before
    public void setup() {
        gameMapper = new GameMapper();
    }

    @Test
    public void testToGameDTO() {
        Game game = new Game(5.4);

        GameDTO gameDTO = gameMapper.toGameDTO(game);

        assertEquals(ERROR_DIFFERENT_BALANCE, game.getBalance(), gameDTO.getBalance());
    }

    @Test
    public void testToBookEntity() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setBalance(BALANCE);

        Game game = gameMapper.toGameEntity(gameDTO);

        assertEquals(ERROR_DIFFERENT_BALANCE, gameDTO.getBalance(), game.getBalance());
    }

}
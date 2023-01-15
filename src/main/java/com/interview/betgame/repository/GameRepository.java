package com.interview.betgame.repository;

import com.interview.betgame.entity.Game;
import com.interview.betgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    Game findByUserId(Long userId);
}

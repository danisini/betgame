package com.interview.betgame.entity;

import com.interview.betgame.convert.StringListConverter;
import com.interview.betgame.convert.StringStackConverter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "currentCardIds")
    @Convert(converter = StringListConverter.class)
    private List<Short> currentCardIds;

    @Column(name = "takenCardIds")
    @Convert(converter = StringStackConverter.class)
    private Stack<Short> takenCardIds;

    @Column(name = "balance")
    private double balance;

    //@OneToOne(fetch = FetchType.LAZY)
    //@MapsId
    //@JoinColumn(name = "id")
    private Long userId;

    public Game () {

    }
    public Game(double balance) {
        this.balance = balance;
        this.takenCardIds = new Stack<Short>();
        this.currentCardIds = new ArrayList<Short>();

        generateInitialCardIds();
    }

    private void generateInitialCardIds() {
        for (short i = 1; i <= 52; i ++) {
            currentCardIds.add(i);
        }

        Collections.shuffle(currentCardIds);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Short> getCurrentCardIds() {
        return currentCardIds;
    }

    public void setCurrentCardIds(List<Short> currentCardIds) {
        this.currentCardIds = currentCardIds;
    }

    public Stack<Short> getTakenCardIds() {
        return takenCardIds;
    }

    public void setTakenCardIds(Stack<Short> takenCardIds) {
        this.takenCardIds = takenCardIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

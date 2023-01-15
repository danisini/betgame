package com.interview.betgame.util;

public class GuessResult {
    private Card card;
    // -1 - not;  0 - draw ; 1 - yes
    private short hasGuessedRight;

    public GuessResult(Card card, short hasGuessedRight) {
        this.card = card;
        this.hasGuessedRight = hasGuessedRight;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public short getHasGuessedRight() {
        return hasGuessedRight;
    }

    public void setHasGuessedRight(short hasGuessedRight) {
        this.hasGuessedRight = hasGuessedRight;
    }
}

package com.interview.betgame.util;

import static java.util.Objects.requireNonNull;

public final class Card {
    private final Short id;
    private final Suit suit;
    private final Rank rank;
    public Card(Short id, Suit suit, Rank rank) {
        this.suit = requireNonNull(suit);
        this.rank = requireNonNull(rank);
        this.id = requireNonNull(id);
    }

    public Short getId() {
        return id;
    }

    public Suit getSuit() {
        return suit;
    }
    public Rank getRank() {
        return rank;
    }

}

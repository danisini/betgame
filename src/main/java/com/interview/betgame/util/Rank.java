package com.interview.betgame.util;

public enum Rank {
        TWO("two", 2),
        THREE("three", 3),
        FOUR("four", 4),
        FIVE("five", 5),
        SIX("six", 6),
        SEVEN("seven", 7),
        EIGHT("eight", 8),
        NINE("nine", 9),
        TEN("ten", 10),
        JACK("jack", 11),
        QUEEN("queen", 12),
        KING("king", 13),
        ACE("ace", 14);

        public final int value;
        public final String name;
        Rank(String name, int value) {
                this.name = name;
                this.value = value;
        }

        public int getValue() {
                int value = 0;
                for (Rank currentRank : Rank.values()) {
                        if (currentRank.name.equals(this.name)) {
                                value = currentRank.value;
                                break;
                        }
                }

                return value;
        }
}

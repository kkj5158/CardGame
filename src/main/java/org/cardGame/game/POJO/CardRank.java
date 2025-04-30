package org.cardGame.game.POJO;

public enum CardRank {
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIR(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9),
    ROYAL_FLUSH(10);

    private final int score;

    CardRank(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

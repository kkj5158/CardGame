package org.cardGame.game;

import java.util.List;

public class Hand {
    private final List<Card> cards;
    private int score;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

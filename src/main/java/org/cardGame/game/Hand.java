package org.cardGame.game;

import java.util.List;

public class Hand {
    private final List<Card> cards;
    private CardRank rank; // 변경 포인트: int → CardRank

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public CardRank getRank() {
        return rank;
    }

    public void setRank(CardRank rank) {
        this.rank = rank;
    }
}
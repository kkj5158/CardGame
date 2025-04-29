package org.cardGame.game;

public class Card {
    private final CardNumber cardNum;
    private final CardSuit cardSuit;

    public Card(CardNumber cardNum, CardSuit cardSuit) {
        this.cardNum = cardNum;
        this.cardSuit = cardSuit;
    }

    public CardNumber getCardNum() {
        return cardNum;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public int getCardNumberValue() {
        return cardNum.getValue();
    }
}

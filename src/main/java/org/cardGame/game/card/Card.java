package org.cardGame.game.card;

import lombok.Getter;

@Getter
public class Card {
    private final CardNumber cardNum;
    private final CardSuit cardSuit;

    public Card(CardNumber cardNum, CardSuit cardSuit) {
        this.cardNum = cardNum;
        this.cardSuit = cardSuit;
    }

    public int getCardNumberValue() {
        return cardNum.getValue();
    }
}

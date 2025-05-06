package org.cardGame.game.card;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Deck {
    private final String deckNo;
    private static final int deckCount = 1;
    private final List<Card> cardList;

    public Deck() {
        this.deckNo = "Deck" + deckCount;
        this.cardList = initializeDeck();
    }

    private List<Card> initializeDeck() {

        List<Card> cards = new ArrayList<>();

        for (CardSuit suit : CardSuit.values()) {
            for (CardNumber number : CardNumber.values()) {
                cards.add(new Card(number, suit));
            }
        }
        return cards;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public Card draw() {
        if (cardList.isEmpty()) {
            throw new IllegalStateException("덱에 카드가 없습니다!");
        }
        return cardList.remove(0);
    }
}

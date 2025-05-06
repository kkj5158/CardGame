package org.cardGame.game.player;

import org.cardGame.game.card.Card;

import java.util.List;

public interface Player {
    void win();
    void lose();
    void clearHands();
    void receiveHand(List<Card> cardList);
}

package org.cardGame.game.dealer;

import org.cardGame.game.card.Card;
import org.cardGame.game.card.Deck;
import org.cardGame.game.player.PlayerImpl;

import java.util.List;
import java.util.Map;

public interface Dealer {
    void receiveDeck(Deck deck);
    void shuffle(Deck deck);
    List<Card> dealCardToPlayer();
    Map<Integer, PlayerImpl> evaluatePlayer(List<PlayerImpl> playerImpls);


}

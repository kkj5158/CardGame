package org.cardGame.game.rule;

import org.cardGame.game.card.Card;
import org.cardGame.game.card.CardRank;
import org.cardGame.game.player.PlayerImpl;

import java.util.List;
import java.util.Map;

public interface HandEvaluator {
    Map<Integer, PlayerImpl> evaluateRanks(List<PlayerImpl> playerImpls);
    CardRank evaluateHand(List<Card> cards);


}

package org.cardGame.game.rule;

import org.cardGame.game.card.Card;
import org.cardGame.game.card.CardRank;
import org.cardGame.game.player.Player;

import java.util.List;
import java.util.Map;

public interface HandEvaluator {
    Map<Integer,Player> evaluateRanks(List<Player> players);
    CardRank evaluateHand(List<Card> cards);


}

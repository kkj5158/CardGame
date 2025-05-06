package org.cardGame.game.dealer;

import lombok.Getter;
import org.cardGame.game.card.Card;
import org.cardGame.game.card.Deck;
import org.cardGame.game.player.PlayerImpl;
import org.cardGame.game.rule.HandEvaluator;
import org.cardGame.game.rule.HandEvaluatorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class DealerImpl implements Dealer {
    private final String name;
    private Deck deck;

    private HandEvaluator handEvaluator;

    public DealerImpl(String name) {
        this.name = name;
        this.handEvaluator = new HandEvaluatorImpl();
    }

    // 사용할 덱을 건네받는다.
    @Override
    public void receiveDeck(Deck deck) {
        this.deck = deck;
    }

    // ===== 덱 셔플 =====
    @Override
    public void shuffle(Deck deck) {
        Collections.shuffle(deck.getCardList());
    }


    // 플레이어 한명에게 카드 5장을 건네준다.
    @Override
    public List<Card> dealCardToPlayer() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(deck.draw());
        }
        return cards;
    }

    @Override
    public Map<Integer, PlayerImpl> evaluatePlayer(List<PlayerImpl> playerImpls) {
        return handEvaluator.evaluateRanks(playerImpls);
    }


}

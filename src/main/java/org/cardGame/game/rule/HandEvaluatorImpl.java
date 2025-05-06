package org.cardGame.game.rule;

import org.cardGame.game.card.Card;
import org.cardGame.game.card.CardRank;
import org.cardGame.game.player.PlayerImpl;

import java.util.*;

public class HandEvaluatorImpl implements HandEvaluator {
    @Override
    public Map<Integer, PlayerImpl> evaluateRanks(List<PlayerImpl> playerImpls) {

        List<PlayerWithRank> evaluated = new ArrayList<>();

        for (PlayerImpl playerImpl : playerImpls) {
            List<Card> hand = playerImpl.getHands();
            CardRank rank = evaluateHand(hand);
            evaluated.add(new PlayerWithRank(playerImpl, hand, rank));
        }

        // 정렬: 높은 족보 우선 → 족보 같으면 숫자 높은 순으로
        for (int i = 0; i < evaluated.size() - 1; i++) {
            for (int j = i + 1; j < evaluated.size(); j++) {

                PlayerWithRank p1 = evaluated.get(i);
                PlayerWithRank p2 = evaluated.get(j);

                int rankCompare = Integer.compare(p2.rank.getScore(), p1.rank.getScore());

                if (rankCompare > 0) {
                    evaluated.set(i, p2);
                    evaluated.set(j, p1);
                } else if (rankCompare == 0) {
                    // 카드 숫자 비교
                    int cardCompare = compareCardNumbers(p2.hand, p1.hand);
                    if (cardCompare > 0) {
                        evaluated.set(i, p2);
                        evaluated.set(j, p1);
                    }
                }
            }
        }

        Map<Integer, PlayerImpl> rankMap = new LinkedHashMap<>();
        for (int i = 0; i < evaluated.size(); i++) {
            rankMap.put(i + 1, evaluated.get(i).playerImpl);
        }

        return rankMap;
    }

    // 카드 랭크 판별하기
    public CardRank evaluateHand(List<Card> cards) {

        int[] counts = new int[15]; // 카드 숫자: 2~14
        int[] suits = new int[4];   // 무늬

        for (Card card : cards) {
            counts[card.getCardNumberValue()]++;
            suits[card.getCardSuit().ordinal()]++;
        }

        boolean flush = false;

        for (int i = 0; i < suits.length; i++) {
            if (suits[i] == 5) {
                flush = true;
                break;
            }
        }

        boolean straight = false;

        for (int i = 2; i <= 10; i++) {
            if (counts[i] == 1 &&
                counts[i + 1] == 1 &&
                counts[i + 2] == 1 &&
                counts[i + 3] == 1 &&
                counts[i + 4] == 1) {
                straight = true;
                break;
            }
        }

        boolean royal = counts[10] == 1 &&
                        counts[11] == 1 &&
                        counts[12] == 1 &&
                        counts[13] == 1 &&
                        counts[14] == 1;

        if (flush && royal) return CardRank.ROYAL_STRAIGHT_FLUSH;
        if (flush && straight) return CardRank.STRAIGHT_FLUSH;

        for (int i = 2; i <= 14; i++) {
            if (counts[i] == 4) return CardRank.FOUR_OF_A_KIND;
        }

        boolean hasThree = false;
        boolean hasTwo = false;

        for (int i = 2; i <= 14; i++) {
            if (counts[i] == 3) hasThree = true;
            if (counts[i] == 2) hasTwo = true;
        }

        if (hasThree && hasTwo) return CardRank.FULL_HOUSE;
        if (flush) return CardRank.FLUSH;
        if (straight) return CardRank.STRAIGHT;
        if (hasThree) return CardRank.THREE_OF_A_KIND;

        int pairCount = 0;
        for (int i = 2; i <= 14; i++) {
            if (counts[i] == 2) pairCount++;
        }

        if (pairCount == 2) return CardRank.TWO_PAIR;
        if (pairCount == 1) return CardRank.ONE_PAIR;

        return CardRank.HIGH_CARD;
    }

    /**
     * 같은 족보일 경우, 카드 숫자 비교
     * 높은 숫자 카드부터 내림차순으로 비교함
     */
    private int compareCardNumbers(List<Card> hand1, List<Card> hand2) {
        int[] ranks1 = new int[hand1.size()];
        int[] ranks2 = new int[hand2.size()];

        for (int i = 0; i < hand1.size(); i++) {
            ranks1[i] = hand1.get(i).getCardNumberValue();
            ranks2[i] = hand2.get(i).getCardNumberValue();
        }

        Arrays.sort(ranks1);
        Arrays.sort(ranks2);

        // 높은 숫자부터 비교 (뒤에서부터)
        for (int i = ranks1.length - 1; i >= 0; i--) {
            if (ranks1[i] > ranks2[i]) return 1;
            if (ranks1[i] < ranks2[i]) return -1;
        }

        return 0; // 완전 동일
    }

    private static class PlayerWithRank {
        PlayerImpl playerImpl;
        List<Card> hand;
        CardRank rank;

        public PlayerWithRank(PlayerImpl playerImpl, List<Card> hand, CardRank rank) {
            this.playerImpl = playerImpl;
            this.hand = hand;
            this.rank = rank;
        }
    }
}

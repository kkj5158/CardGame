package org.cardGame.game;

import java.util.*;
import java.util.stream.Collectors;

public class Dealer {
    private final String name;

    public Dealer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void shuffle(Deck deck) {
        Collections.shuffle(deck.getCardList());
    }

    // 플레이어들에게 5장씩 카드 나눠주기
    public List<Card> dealCards(Deck deck) {

        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            cards.add(deck.draw());
        }
        return cards;
    }

    private int compareSameRankHands(List<Card> hand1, List<Card> hand2, CardRank rank) {
        List<Integer> ranks1 = hand1.stream()
                .map(Card::getCardNumberValue)
                .sorted(Comparator.reverseOrder()) // 높은 카드부터
                .collect(Collectors.toList());

        List<Integer> ranks2 = hand2.stream()
                .map(Card::getCardNumberValue)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        switch (rank) {
            case STRAIGHT:
            case STRAIGHT_FLUSH:
                return compareHighestCard(ranks1, ranks2);

            case FLUSH:
            case HIGH_CARD:
                return compareAllCards(ranks1, ranks2);

            case FOUR_OF_A_KIND:
            case FULL_HOUSE:
            case THREE_OF_A_KIND:
            case TWO_PAIR:
            case ONE_PAIR:
                return compareGroupedHands(ranks1, ranks2);

            default:
                return 0; // 무승부
        }
    }

    private int compareHighestCard(List<Integer> ranks1, List<Integer> ranks2) {
    if (ranks1.get(0) > ranks2.get(0)) {
        return 1;
    } else if (ranks1.get(0) < ranks2.get(0)) {
        return -1;
    }
    return 0;
}

private int compareGroupedHands(List<Integer> ranks1, List<Integer> ranks2) {
    Map<Integer, Long> group1 = ranks1.stream()
            .collect(Collectors.groupingBy(r -> r, Collectors.counting()));
    Map<Integer, Long> group2 = ranks2.stream()
            .collect(Collectors.groupingBy(r -> r, Collectors.counting()));

    List<Map.Entry<Integer, Long>> list1 = group1.entrySet().stream()
            .sorted((a, b) -> {
                int countCompare = Long.compare(b.getValue(), a.getValue()); // 먼저 몇 장 묶였는지
                if (countCompare == 0) {
                    return Integer.compare(b.getKey(), a.getKey()); // 같으면 높은 카드 우선
                }
                return countCompare;
            }).collect(Collectors.toList());

    List<Map.Entry<Integer, Long>> list2 = group2.entrySet().stream()
            .sorted((a, b) -> {
                int countCompare = Long.compare(b.getValue(), a.getValue());
                if (countCompare == 0) {
                    return Integer.compare(b.getKey(), a.getKey());
                }
                return countCompare;
            }).collect(Collectors.toList());

    for (int i = 0; i < list1.size(); i++) {
        int rank1 = list1.get(i).getKey();
        int rank2 = list2.get(i).getKey();
        if (rank1 > rank2) {
            return 1;
        } else if (rank1 < rank2) {
            return -1;
        }
    }
    return 0;
}


private int compareAllCards(List<Integer> ranks1, List<Integer> ranks2) {
    for (int i = 0; i < ranks1.size(); i++) {
        if (ranks1.get(i) > ranks2.get(i)) {
            return 1;
        } else if (ranks1.get(i) < ranks2.get(i)) {
            return -1;
        }
    }
    return 0;
}



      public CardRank evaluateHand(List<Card> cards) {

        // 먼저 rank value 기준으로 오름차순 정렬
        List<Integer> ranks = cards.stream()
                .map(Card::getCardNumberValue)
                .sorted()
                .collect(Collectors.toList());

        boolean isFlush = isFlush(cards);
        boolean isStraight = isStraight(ranks);

        if (isFlush && isRoyal(ranks)) {
            return CardRank.ROYAL_FLUSH;
        } else if (isFlush && isStraight) {
            return CardRank.STRAIGHT_FLUSH;
        } else if (isFourOfAKind(ranks)) {
            return CardRank.FOUR_OF_A_KIND;
        } else if (isFullHouse(ranks)) {
            return CardRank.FULL_HOUSE;
        } else if (isFlush) {
            return CardRank.FLUSH;
        } else if (isStraight) {
            return CardRank.STRAIGHT;
        } else if (isThreeOfAKind(ranks)) {
            return CardRank.THREE_OF_A_KIND;
        } else if (isTwoPair(ranks)) {
            return CardRank.TWO_PAIR;
        } else if (isOnePair(ranks)) {
            return CardRank.ONE_PAIR;
        } else {
            return CardRank.HIGH_CARD;
        }
    }

    private boolean isFlush(List<Card> cards) {
        CardSuit firstCardSuit = cards.get(0).getCardSuit();
        return cards.stream().allMatch(card -> card.getCardSuit() == firstCardSuit);
    }

    private boolean isStraight(List<Integer> ranks) {
        for (int i = 0; i < ranks.size() - 1; i++) {
            if (ranks.get(i) + 1 != ranks.get(i + 1)) {
                // 특수 케이스: A, 2, 3, 4, 5 스트레이트 처리는 나중에 추가할 수 있음
                return false;
            }
        }
        return true;
    }

    private boolean isRoyal(List<Integer> ranks) {
        List<Integer> royalRanks = Arrays.asList(10, 11, 12, 13, 14); // T, J, Q, K, A
        return ranks.equals(royalRanks);
    }

    private boolean isFourOfAKind(List<Integer> ranks) {
        return hasNOfAKind(ranks, 4);
    }

    private boolean isThreeOfAKind(List<Integer> ranks) {
        return hasNOfAKind(ranks, 3);
    }

    private boolean isOnePair(List<Integer> ranks) {
        return countPairs(ranks) == 1;
    }

    private boolean isTwoPair(List<Integer> ranks) {
        return countPairs(ranks) == 2;
    }

    private boolean isFullHouse(List<Integer> ranks) {
        Map<Integer, Long> grouped = ranks.stream()
                .collect(Collectors.groupingBy(r -> r, Collectors.counting()));
        return grouped.containsValue(3L) && grouped.containsValue(2L);
    }

    private boolean hasNOfAKind(List<Integer> ranks, int n) {
        return ranks.stream()
                .collect(Collectors.groupingBy(r -> r, Collectors.counting()))
                .containsValue((long) n);
    }

    private int countPairs(List<Integer> ranks) {
        return (int) ranks.stream()
                .collect(Collectors.groupingBy(r -> r, Collectors.counting()))
                .values().stream()
                .filter(count -> count == 2)
                .count();
    }


}

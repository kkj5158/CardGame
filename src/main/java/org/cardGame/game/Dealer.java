package org.cardGame.game;

import org.cardGame.game.POJO.Card;
import org.cardGame.game.POJO.CardRank;
import org.cardGame.game.POJO.CardSuit;
import org.cardGame.game.POJO.Hand;

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

    // ===== 덱 셔플 =====
    public void shuffle(Deck deck) {
        Collections.shuffle(deck.getCardList());
    }

    // ===== 플레이어들에게 카드 5장씩 나눠주기 =====
    public Map<Player, Hand> dealToPlayers(List<Player> players, Deck deck) {
        shuffle(deck);
        Map<Player, Hand> playerHands = new HashMap<>();
        for (Player player : players) {
            List<Card> cards = dealCards(deck);
            playerHands.put(player, new Hand(cards));
        }
        return playerHands;
    }

    private List<Card> dealCards(Deck deck) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(deck.draw());
        }
        return cards;
    }

    // ===== 플레이어 핸드 평가 및 최종 승자 판별 =====
    public Player decideWinner(Map<Player, Hand> playerHands) {
        List<Map.Entry<Player, Hand>> players = new ArrayList<>(playerHands.entrySet());

        // 핸드 족보 평가
        for (Map.Entry<Player, Hand> entry : players) {
            CardRank rank = evaluateHand(entry.getValue().getCards());
            entry.getValue().setRank(rank);
        }

        // 정렬: 높은 족보 우선 → 같은 족보면 카드 숫자 비교
        players.sort((p1, p2) -> {
            int rankCompare = Integer.compare(
                    p2.getValue().getRank().getScore(),
                    p1.getValue().getRank().getScore()
            );
            if (rankCompare != 0) {
                return rankCompare;
            }
            // 족보가 같으면 카드 숫자 비교
            return compareSameRankHands(
                    p1.getValue().getCards(),
                    p2.getValue().getCards(),
                    p1.getValue().getRank()
            );
        });

        // 제일 앞에 있는 플레이어가 승자
        return players.get(0).getKey();
    }

    // ===== 족보 평가 =====
    public CardRank evaluateHand(List<Card> cards) {
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

    // ===== 족보별 상세 비교 =====
    private int compareSameRankHands(List<Card> hand1, List<Card> hand2, CardRank rank) {
        List<Integer> ranks1 = hand1.stream()
                .map(Card::getCardNumberValue)
                .sorted(Comparator.reverseOrder())
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
                return 0;
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

    private int compareGroupedHands(List<Integer> ranks1, List<Integer> ranks2) {
        Map<Integer, Long> group1 = ranks1.stream()
                .collect(Collectors.groupingBy(r -> r, Collectors.counting()));
        Map<Integer, Long> group2 = ranks2.stream()
                .collect(Collectors.groupingBy(r -> r, Collectors.counting()));

        List<Map.Entry<Integer, Long>> list1 = group1.entrySet().stream()
                .sorted((a, b) -> {
                    int countCompare = Long.compare(b.getValue(), a.getValue());
                    if (countCompare == 0) {
                        return Integer.compare(b.getKey(), a.getKey());
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

    // ===== 보조 메서드 (족보 판별용) =====
    private boolean isFlush(List<Card> cards) {
        CardSuit firstSuit = cards.get(0).getCardSuit();
        return cards.stream().allMatch(card -> card.getCardSuit() == firstSuit);
    }

    private boolean isStraight(List<Integer> ranks) {
        for (int i = 0; i < ranks.size() - 1; i++) {
            if (ranks.get(i) + 1 != ranks.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRoyal(List<Integer> ranks) {
        List<Integer> royalRanks = Arrays.asList(10, 11, 12, 13, 14);
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

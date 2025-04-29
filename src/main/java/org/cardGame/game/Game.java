package org.cardGame.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private static int gameCount = 1;
    private final String gameNo;
    private final Map<Player, Hand> playerHands = new HashMap<>();
    private final List<Player> players;
    private final Dealer dealer;
    private final Deck deck;


    public Game(List<Player> players, Dealer dealer) {
        this.gameNo = "Game" + gameCount;
        this.players = players;
        this.dealer = dealer;
        this.deck = new Deck();
        gameCount++;
    }

    public void start(List<Player> players, Dealer dealer) {

        System.out.println("======== gameNo" + "시작됩니다. ========");

        // 딜러가 카드를 플레이어들에게 나눠준다.
        for (Player p : players) {
            dealer.shuffle(deck);
            List<Card> cards = dealer.dealCards(deck);
            playerHands.put(p, new Hand(cards));
        }
        // 딜러가 각각의 플레이어의 핸드카드에 점수를 부여합니다.
        for (Player p : players) {
              Hand hand = playerHands.get(p);
            int score = dealer.evaluateCards(hand.getCards());
            hand.setScore(score);
        }


    }


    private
}

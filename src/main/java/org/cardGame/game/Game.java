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

      public GameResult start( ) {
        System.out.println("======== Game " + gameCount + " 시작됩니다. ========");

        // 1. 딜러가 덱 셔플하고 플레이어에게 카드 나눠주기
        playerHands.clear(); // 매 게임마다 초기화
        playerHands.putAll(dealer.dealToPlayers(players, deck));

        // 2. 딜러가 플레이어들의 핸드를 평가하고 최종 승자 결정
        Player winner = dealer.decideWinner(playerHands);

        // 3. 승자, 패자 기록 업데이트
        updatePlayerRecords(winner);

        // 4. 게임 결과 출력
        printGameResult(winner);

        GameResult gameResult = new GameResult(gameCount, winner);
        return  gameResult;
    }

    private void updatePlayerRecords(Player winner) {
        for (Player player : players) {
            if (player.equals(winner)) {
                player.win();
            } else {
                player.lose();
            }
        }
    }

    private void printGameResult(Player winner) {
        System.out.println("🏆 승자: " + winner.getNickName());
        System.out.println("--- 플레이어 상태 ---");
        for (Player player : players) {
            System.out.println(player.getNickName() + " | 승: " + player.getCountOfWin() + " 패: " + player.getCountOfLoss());
        }
    }





}

package org.cardGame.game.game;

import org.cardGame.game.card.Card;
import org.cardGame.game.card.Deck;
import org.cardGame.game.dealer.Dealer;
import org.cardGame.game.player.PlayerImpl;

import java.util.List;
import java.util.Map;

public class Game {
    private static int gameCount = 0;
    private final String gameNo;
    private final List<PlayerImpl> playerImpls;
    private final Dealer dealer;
    private final Deck deck;

    public Game(List<PlayerImpl> playerImpls, Dealer dealer) {
        this.gameNo = "Game" + gameCount;
        this.playerImpls = playerImpls;
        this.dealer = dealer;
        this.deck = new Deck();
        gameCount++;
    }

    public GameResult start() {
        System.out.println("======== Game " + gameCount + " 시작됩니다. ========");

        // 1, 딜러는 덱을 건네받는다.
        this.dealer.receiveDeck(deck);

        // 2. 딜러가 덱 셔플하고 플레이어에게 카드 나눠주기 ( 4 명의 플레이어는 연달아서 카드를 분배 받는다.)
        for (PlayerImpl p : playerImpls) {
            dealer.shuffle(deck); // 딜러가 덱을 셔플한다.
            List<Card> cards = dealer.dealCardToPlayer(); // 딜러가 5장 카드 뽑아내기
            p.receiveHand(cards); // 플레이어는 해당 카드를 받는다.
        }

        // 3. 딜러가 플레이어들의 핸드를 평가하고 순위를 건넨다.
        Map<Integer, PlayerImpl> ranks = dealer.evaluatePlayer(playerImpls);

        // 4. 승자 선출하기
        PlayerImpl winner = ranks.get(1);

        // 5. 승자, 패자 기록 업데이트
        updatePlayerRecords(winner);

        // 6. 게임 결과 출력
        printGameResult(winner);

        GameResult gameResult = new GameResult(gameCount, winner);

        // 7. 게임이 끝나면 플레이어는 카드를 비운다.

        return gameResult;
    }

    private void updatePlayerRecords(PlayerImpl winner) {
        for (PlayerImpl playerImpl : playerImpls) {
            if (playerImpl.equals(winner)) {
                playerImpl.win();
            } else {
                playerImpl.lose();
            }
        }
    }

    private void printGameResult(PlayerImpl winner) {
        System.out.println(gameNo + "승자🏆: " + winner.getNickName());
        System.out.println("--- 플레이어 상태 ---");
        for (PlayerImpl playerImpl : playerImpls) {
            System.out.println(playerImpl.getNickName() + " | 승: " + playerImpl.getCountOfWin() + " 패: " + playerImpl.getCountOfLoss());
        }
    }


}

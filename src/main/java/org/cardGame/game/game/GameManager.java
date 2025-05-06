package org.cardGame.game.game;

import org.cardGame.game.dealer.Dealer;
import org.cardGame.game.dealer.DealerImpl;
import org.cardGame.game.helper.NickNameHelper;
import org.cardGame.game.player.PlayerImpl;

import java.util.*;

public class GameManager {
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;
    private static final double DEFAULT_MONEY = 10000;
    private final List<GameResult> gameResults = new ArrayList<>();
    private final List<PlayerImpl> playerImpls = new ArrayList<>();
    private final NickNameHelper nickNameHelper;
    private int numOfPlayers;
    private int numOfGmaes;
    public GameManager() {
        this.nickNameHelper = new NickNameHelper();
    }

    public void start() {
        Scanner sc = new Scanner(System.in);


        System.out.print("전체 게임수를 입력해주세요: ");
        this.numOfGmaes = sc.nextInt();

        sc.nextLine(); // 개행 문자 비우기

        System.out.print("참여 플레이어 수를 입력해주세요: ");
        this.numOfPlayers = sc.nextInt();

        sc.nextLine(); // 개행 문자 비우기

        if (numOfPlayers > MAX_PLAYERS || numOfPlayers < MIN_PLAYERS) {
            System.out.println("2명 ~ 4명의 플레이어만 참여가능합니다.");
        }

        // 플레이어 셋팅

        for (int i = 0; i < numOfPlayers; i++) {
            PlayerImpl playerImpl = new PlayerImpl(nickNameHelper.generateUniqueNickname(), DEFAULT_MONEY);
            playerImpls.add(playerImpl);
        }

        System.out.println("==== 카드 게임 시뮬레이션 시작 됩니다 ====");
        System.out.println("플레이어수 : " + numOfPlayers);
        System.out.println("전체게임수 : " + numOfGmaes);

        for (int i = 0; i < numOfGmaes; i++) {
            Dealer dealer = new DealerImpl(nickNameHelper.generateDealerName());
            Game game = new Game(playerImpls, dealer);
            GameResult gameResult = game.start();
            gameResults.add(gameResult);
        }


    }


    public void printFinalWinner() {
        Map<PlayerImpl, Integer> sessionWinCounts = new HashMap<>();

        // 1. 승자 집계
        for (GameResult result : gameResults) {
            PlayerImpl winner = result.getWinner();
            sessionWinCounts.put(winner, sessionWinCounts.getOrDefault(winner, 0) + 1);
        }

        // 2. 승자 없는 경우 처리
        if (sessionWinCounts.isEmpty()) {
            System.out.println("승자가 없습니다.");
            return;
        }

        // 3. 최고 승수 찾기
        int maxWins = 0;
        for (int wins : sessionWinCounts.values()) {
            if (wins > maxWins) {
                maxWins = wins;
            }
        }

        // 4. 최고 승수 가진 플레이어 수집
        List<PlayerImpl> winners = new ArrayList<>();
        for (Map.Entry<PlayerImpl, Integer> entry : sessionWinCounts.entrySet()) {
            if (entry.getValue() == maxWins) {
                winners.add(entry.getKey());
            }
        }

        // 5. 출력
        System.out.println("🏆 이번 세션 최종 승자(공동 포함): ");
        for (PlayerImpl winner : winners) {
            System.out.println("- " + winner.getNickName() + " | 승리 수: " + sessionWinCounts.get(winner));
        }
    }


}

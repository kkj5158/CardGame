package org.cardGame.game;

import java.util.*;

public class GameManager {
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 4;

    private static final double DEFAULT_MONEY = 4;


    private static final String[] dealerNames = {
            "Jack Carter",
            "Sam Wilson",
            "Mike Johnson",
            "Emily Davis",
            "Chris Taylor",
            "Olivia Brown",
            "James Miller",
            "Sophia Anderson",
            "Ethan Harris",
            "Ava Martin"
    };
    private static final String[] adjectives = {
            "Lucky", "Fearless", "Sharp", "Cold", "Bold", "Silent", "Cunning", "Mighty"
    };

    private static final String[] nouns = {
            "Ace", "King", "Queen", "Jack", "Card", "Dealer", "Gambler", "Shark"
    };


    private final Set<String> nicknameSet = new HashSet<>();

    private List<Player> players;
    private Dealer dealer;
    private int numOfPlayers;
    private int numOfGmaes;

    public void start() {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

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
            Player player = new Player(generateUniqueNickname(), DEFAULT_MONEY);
            players.add(player);
        }

        Dealer mainDealer = new Dealer(dealerNames[random.nextInt(dealerNames.length)]);

        System.out.println("==== 카드 게임 시뮬레이션 시작 됩니다 ====");
        System.out.println("숙련된 딜러 - " + dealer.getName() + "가 게임을 진행합니다.");
        System.out.println("플레이어수 : " + numOfPlayers);
        System.out.println("전체게임수 : " + numOfGmaes);

        for (int i = 0; i < numOfGmaes; i++) {
            Game game = new Game();
            game.start(players, mainDealer);
        }


    }


    public String generateUniqueNickname() {
        Random random = new Random();

        while (true) {
            String adjective = adjectives[random.nextInt(adjectives.length)];
            String noun = nouns[random.nextInt(nouns.length)];
            int number = random.nextInt(1000, 9999); // 1000~9999

            String nickname = adjective + noun + number;

            if (!nicknameSet.contains(nickname)) {
                nicknameSet.add(nickname);
                return nickname;
            }
        }
    }

    // 플레이어 세팅
}

package org.cardGame.game.helper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NickNameHelper {
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

    Random random = new Random();
    private final Set<String> nicknameSet = new HashSet<>();

    // 플레이어 닉네임 생성 함수
    public String generateUniqueNickname() {


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

    public String generateDealerName() {
        Random random = new Random();

        return dealerNames[random.nextInt(dealerNames.length)];
    }
}

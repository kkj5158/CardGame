package org.cardGame.game.player;

import lombok.Getter;
import org.cardGame.game.card.Card;

import java.util.List;


@Getter
public class PlayerImpl implements Player {

    private static final double REWARD = 100.0; // 승리 시 상금 100원
    private final String nickName;
    private double money;
    private double winRate;
    private int countOfWin;
    private int countOfLoss;
    private int countOfGames;
    private List<Card> hands;


    public PlayerImpl(String nickName, double money) {
        this.nickName = nickName;
        this.money = money;
    }

    @Override
    public void win() {
        countOfWin++;
        countOfGames++;
        money += REWARD;
        updateWinRate();
    }

    @Override
    public void lose() {
        countOfLoss++;
        countOfGames++;
        updateWinRate();
    }

    @Override
    public void clearHands() {
        hands.clear();
    }

    @Override
    public void receiveHand(List<Card> cardList) {
        this.hands = cardList;
    }

    private void updateWinRate() {
        if (countOfGames == 0) {
            winRate = 0.0;
        } else {
            winRate = (double) countOfWin / countOfGames * 100; // 승률 %로 표시
        }
    }


}

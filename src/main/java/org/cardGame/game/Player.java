package org.cardGame.game;

import java.util.List;

public class Player {
    private final String nickName;
    private double money;
    private double winRate;
    private int countOfWin;
    private int countOfLoss;
    private int countOfGames;

    private static final double REWARD = 100.0; // 승리 시 상금 100원

    public Player(String nickName, double money) {
        this.nickName = nickName;
        this.money = money;
    }

    public void win() {
        countOfWin++;
        countOfGames++;
        money += REWARD;
        updateWinRate();
    }

    public void lose() {
        countOfLoss++;
        countOfGames++;
        updateWinRate();
    }

    private void updateWinRate() {
        if (countOfGames == 0) {
            winRate = 0.0;
        } else {
            winRate = (double) countOfWin / countOfGames * 100; // 승률 %로 표시
        }
    }

    public String getNickName() {
        return nickName;
    }

    public double getMoney() {
        return money;
    }

    public double getWinRate() {
        return winRate;
    }

    public int getCountOfWin() {
        return countOfWin;
    }

    public int getCountOfLoss() {
        return countOfLoss;
    }

    public int getCountOfGames() {
        return countOfGames;
    }

}

package org.cardGame.game;

import java.util.List;

public class Player {
    private String nickName;
    private double money;
    private double winRate;
    private int countOfWin;
    private int countOfLoss;
    private int countOfGames;

    public Player(String nickName, double money) {
        this.nickName = nickName;
        this.money = money;
    }

    public void win(){

    }
    public void calculateWin(){

    }

    public void plusWin(){

    }

    public void plusMoney(){

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

    public int getCountOfGames() {
        return countOfGames;
    }

}

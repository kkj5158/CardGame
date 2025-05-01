package org.cardGame;

import org.cardGame.game.game.GameManager;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.start();
        gameManager.printFinalWinner();

    }
}
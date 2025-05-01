package org.cardGame.game.game;

import org.cardGame.game.player.Player;

public class GameResult {
    private final int gameNumber;
    private final Player winner;

    public GameResult(int gameNumber, Player winner) {
        this.gameNumber = gameNumber;
        this.winner = winner;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public Player getWinner() {
        return winner;
    }
}

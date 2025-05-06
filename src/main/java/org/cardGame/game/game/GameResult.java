package org.cardGame.game.game;

import org.cardGame.game.player.PlayerImpl;

public class GameResult {
    private final int gameNumber;
    private final PlayerImpl winner;

    public GameResult(int gameNumber, PlayerImpl winner) {
        this.gameNumber = gameNumber;
        this.winner = winner;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public PlayerImpl getWinner() {
        return winner;
    }
}

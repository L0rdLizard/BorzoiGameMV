package ru.maxim.borzoi.gameMain;

import org.junit.jupiter.api.Test;
import ru.maxim.borzoi.entities.Ball;
import ru.maxim.borzoi.gameStates.GameStates;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;

class GameTest {
    @Test
    public void ifPlayerAfkEmemiesShouldKillPlayer() {
        Game game = new Game();
        GameStates.state = GameStates.PLAYING;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
        assertTrue(game.getPlaying().getGameOver());
    }

    @Test
    public void ifPlayerDieIfTouchSpike() {
        Game game = new Game();
        GameStates.state = GameStates.PLAYING;
        for (Ball b : game.getPlaying().getEnemyManager().getBalls()) {
            b.hurt(1000);
        }
        game.getPlaying().getPlayer().changeHealth(-2);
        game.getPlaying().loadNextLevel();
        assertEquals(3, game.getPlaying().getPlayer().getCurrentHealth());
    }

    @Test
    public void gameOverShouldBeTrueAfterKillPlayer() {
        Game game = new Game();
        GameStates.state = GameStates.PLAYING;
        game.getPlaying().getPlayer().changeHealth(-200);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        assertTrue(game.getPlaying().getGameOver());

    }

    @Test
    public void lvlCompletedShouldBeTrueAfterKillAllEnemies() {
        Game game = new Game();
        GameStates.state = GameStates.PLAYING;
        for (Ball b : game.getPlaying().getEnemyManager().getBalls()) {
            b.hurt(1000);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        assertTrue(game.getPlaying().getLvlCompleted());
    }
}
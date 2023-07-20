package ru.maxim.borzoi.entities;

import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.levels.Level;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.utilz.LoadSave;

import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import static ru.maxim.borzoi.utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private ArrayList<Ball> balls = new ArrayList<>();


    public EnemyManager(Playing playing){
        this.playing = playing;
    }

    public void loadEnemies(Level level) {
        balls = level.getBalls();
//        System.out.println("size of ball: " + balls.size());
    }

    public void update(int[][] lvlData, Player player){
        boolean isAnyActive = false;
        for (Ball b : balls)
            if(b.isActive()) {
                b.update(lvlData, player);
                isAnyActive = true;
            } else if (b.isCanDropCoin()) {
                b.setCanDropCoin(false);
                playing.createCoin((int) b.getHitbox().x, (int) b.getHitbox().y, b.enemyType);
            }
        if (!isAnyActive)
            playing.setLevelCompleted(true);
    }


    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Ball b : balls)
            if (b.isActive())
                if (attackBox.intersects(b.getHitbox())) {
                    b.hurt(1);
                    return;
                }
    }


    public void resetAllEnemies() {
        for (Ball c : balls)
            c.resetEnemy();
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
}

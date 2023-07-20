package ru.maxim.borzoi.views;

import ru.maxim.borzoi.entities.Ball;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ru.maxim.borzoi.utilz.Constants.EnemyConstants.*;


public class EnemyManagerView {

    private BufferedImage[][] ballImgs;
    private Playing playing;
    private float xDrawOffset = 36 * Game.SCALE;
    private float yDrawOffset = 29 * Game.SCALE;

    public EnemyManagerView(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset){
        drawBall(g, xLvlOffset, yLvlOffset);
    }

    private void drawBall(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Ball b : playing.getEnemyManager().getBalls()){
            if (b.isActive()) {
                g.drawImage(ballImgs[b.getEnemyState()][b.getAnimIndex()],
                        (int) (b.getHitbox().x) - xLvlOffset - BALL_DRAWOFFSET_X + b.flipX(),
                        (int) (b.getHitbox().y) - yLvlOffset - BALL_DRAWOFFSET_Y,
                        BALL_WIDTH * b.flipW(), BALL_HEIGHT, null);
//                drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
        }
    }

    private void drawAttackBox(Graphics g, int lvlOffset, int yLvlOffset) {
        for (Ball b : playing.getEnemyManager().getBalls()) {
            g.setColor(Color.black);
            g.drawRect((int) b.attackBox.x - lvlOffset, (int) b.attackBox.y - yLvlOffset, (int) b.attackBox.width, (int) b.attackBox.height);
        }
    }

    private void loadEnemyImages() {
        ballImgs = new BufferedImage[5][6];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.BALL_ATLAS);
        for (int j = 0; j < ballImgs.length; j++){
            for (int i = 0; i < ballImgs[0].length; i++){
                ballImgs[j][i] = temp.getSubimage(i * BALL_WIDTH_DEFAULT, j * BALL_HEIGHT_DEFAULT, BALL_WIDTH_DEFAULT, BALL_HEIGHT_DEFAULT);
            }
        }
    }



}
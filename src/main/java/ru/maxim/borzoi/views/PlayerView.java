package ru.maxim.borzoi.views;

import ru.maxim.borzoi.entities.Player;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PlayerView {

    private BufferedImage hpBarImg;
    private BufferedImage coinBarImg;
    private float xDrawOffset = 36 * Game.SCALE;
    private float yDrawOffset = 29 * Game.SCALE;
    public boolean doubleJump = false;
    public int jumps = 0;

    private int flipX = 0;
    private int flipW = 1;

    // HP bar
    private int hpBarWidth = (int) (96 * Game.SCALE);
    private int hpBarHeight = (int) (32 * Game.SCALE);

    private int coinBarWidth = (int) (38 * Game.SCALE);
    private int coinBarHeight = (int) (38 * Game.SCALE);
    private int hpBarX = (int) (10 * Game.SCALE);
    private int hpBarY = (int) (10 * Game.SCALE);


    private Player player;
    private BufferedImage[][] animations;
    private BufferedImage statusBarImg;
    private Rectangle2D.Float attackBox;
    private Playing playing;


    public PlayerView(Playing playing) {
        this.player = playing.getPlayer();
        this.playing = playing;
        loadAnimation();
    }

    public void update() {
        updateHealthBar();
        if (player.getCurrentHealth() <= 0) {
            playing.setGameOver(true);
        }
    }
    private void updateHealthBar() {
//        System.out.println("update");
        switch (playing.getPlayer().getCurrentHealth()){
            case 3:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3);
//                System.out.println("3");
                break;
            case 2:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3_1);
//                System.out.println("2");
                break;
            case 1:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3_2);
//                System.out.println("1");
                break;
            case 0:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3_3);
                break;
            default:
                hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3_3);
                break;
        }
    }

    public void render(Graphics g, int lvlOffset, int yLvlOffset) {
//        g.drawImage(animations[playerAction][animIndex],
//                (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX,
//                (int) (hitbox.y - yDrawOffset) - yLvlOffset,
//                width * flipW, height, null);
//        drawHitbox(g, lvlOffset, yLvlOffset);
//        drawAttackBox(g, lvlOffset, yLvlOffset);
        updateHealthBar();
        g.drawImage(animations[player.getPlayerAction()][player.getAnimIndex()], (int) (player.getHitbox().x - xDrawOffset) - lvlOffset + playing.getPlayer().getFlipX(), (int) (player.getHitbox().y - yDrawOffset) - yLvlOffset, player.getWidth() * playing.getPlayer().getFlipW(), player.getHeight(), null);
        drawUI(g);
    }

    private void drawUI(Graphics g) {
        coinBarImg = LoadSave.GetSpriteAtlas(LoadSave.COIN);
        g.drawImage(hpBarImg, hpBarX, hpBarY, hpBarWidth, hpBarHeight, null);
        g.drawImage(coinBarImg, hpBarX - 6, hpBarY + hpBarHeight + 10, coinBarWidth, coinBarHeight, null);
        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(" " + playing.getCoins(), hpBarX + coinBarWidth, hpBarY + hpBarHeight + 57);
    }

    private void drawAttackBox(Graphics g, int lvlOffset, int yLvlOffset) {
        g.setColor(Color.black);
        g.drawRect((int) attackBox.x - lvlOffset, (int) attackBox.y - yLvlOffset, (int) attackBox.width, (int) attackBox.height);
    }

    private void loadAnimation(){

        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[5][6];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
//                animations[j][i] = image.getSubimage(i * 32, j * 24, 32, 24);
//                animations[j][i] = image.getSubimage(i * 64, j * 48, 64, 48);
                animations[j][i] = image.getSubimage(i * 80, j * 64, 80, 64);
            }
        }
        hpBarImg = LoadSave.GetSpriteAtlas(LoadSave.HP_BAR3);
    }
}

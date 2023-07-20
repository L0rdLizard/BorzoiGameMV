package ru.maxim.borzoi.views;

import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameStates.Playing;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameOverOverlayView {

    private Playing playing;
    private BufferedImage backgroundImg;
    private int imgX, imgY, imgWidth, imgHeight;
    private UrmButtonView menu, play;

    public GameOverOverlayView(Playing playing){
        this.playing = playing;
    }


    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
        g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH / 2, 300);

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameStates.state = GameStates.MENU;
        }
    }
}

package ru.maxim.borzoi.views;

import ru.maxim.borzoi.UI.UrmButton;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.utilz.LoadSave;
import ru.maxim.borzoi.gameStates.Playing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static ru.maxim.borzoi.utilz.Constants.UI.URMButtons.URM_SIZE;

public class LevelCompletedOverlayView {

    private Playing playing;
    private UrmButtonView menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;


    public LevelCompletedOverlayView(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (395 * Game.SCALE);
        int nextX = (int) (510 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new UrmButtonView(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtonView(menuX, y, URM_SIZE, URM_SIZE, 2);
    }


    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }

    public void update(){
        next.update();
        menu.update();
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn((playing.getLevelCompletedOverlay().getMenu()), e))
            menu.setMouseOver(true);
        else if (isIn((playing.getLevelCompletedOverlay().getNext()), e))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn((playing.getLevelCompletedOverlay().getMenu()), e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                GameStates.state = GameStates.MENU;
            }
        } else if (isIn((playing.getLevelCompletedOverlay().getNext()), e))
            if (next.isMousePressed())
                playing.loadNextLevel();

        menu.resetBools();
        next.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn((playing.getLevelCompletedOverlay().getMenu()), e))
            menu.setMousePressed(true);
        else if (isIn((playing.getLevelCompletedOverlay().getNext()), e))
            next.setMousePressed(true);
    }
}

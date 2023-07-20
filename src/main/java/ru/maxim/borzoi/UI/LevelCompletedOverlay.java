package ru.maxim.borzoi.UI;

import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;

import static ru.maxim.borzoi.utilz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {

    private UrmButton menu, next;

    public LevelCompletedOverlay(){
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (395 * Game.SCALE);
        int nextX = (int) (510 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }


    public void update(){
        next.update();
        menu.update();
    }

    public UrmButton getMenu() {
        return menu;
    }

    public UrmButton getNext() {
        return next;
    }

}

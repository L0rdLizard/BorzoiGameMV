package ru.maxim.borzoi.views;

import ru.maxim.borzoi.UI.PauseButton;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ru.maxim.borzoi.utilz.Constants.UI.URMButtons.URM_SIZE;
import static ru.maxim.borzoi.utilz.Constants.UI.URMButtons.URM_DEFAULT_SIZE;

public class UrmButtonView extends PauseButton   {

    private int rowIndex, index;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;

    public UrmButtonView(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }


    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i*URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }

    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}

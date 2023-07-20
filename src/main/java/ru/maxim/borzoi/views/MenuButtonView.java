package ru.maxim.borzoi.views;

import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ru.maxim.borzoi.utilz.Constants.UI.Buttons.*;

public class MenuButtonView {
    private BufferedImage[] imgs;
    private GameStates state;
    private boolean mouseOver, mousePressed;

    private int xPos, yPos, rowIndex, index;
    private int xOffsetcenter = B_WIDTH / 2;
    public MenuButtonView(int xPos, int yPos, int rowIndex) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs(){
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for(int i = 0; i < imgs.length; i++){
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g){
        g.drawImage(imgs[index], xPos - xOffsetcenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    public void update(){
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public int getRowIndex() {
        return rowIndex;
    }
}

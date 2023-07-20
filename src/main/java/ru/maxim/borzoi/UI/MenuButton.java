package ru.maxim.borzoi.UI;

import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameStates.Menu;

import java.awt.Rectangle;

import static ru.maxim.borzoi.utilz.Constants.UI.Buttons.*;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetcenter = B_WIDTH / 2;
    private Rectangle bounds;
    private GameStates state;
    public MenuButton(int xPos, int yPos, int rowIndex, GameStates state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetcenter, yPos, B_WIDTH, B_HEIGHT);
    }


    public void update(){

    }

    public Rectangle getBounds(){
        return bounds;
    }
    public void applyGameState(){
        GameStates.state = state;
    }

    public GameStates getState() {
        return state;
    }

}

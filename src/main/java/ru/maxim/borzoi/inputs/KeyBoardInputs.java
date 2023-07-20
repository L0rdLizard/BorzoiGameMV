package ru.maxim.borzoi.inputs;

import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameMain.GamePanel;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyBoardInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeyBoardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameStates.state){
            case MENU:
                gamePanel.getGameView().getMenuView().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGameView().getPlayingView().keyReleased(e);
                break;
            case EDIT:
                gamePanel.getGameView().getEditingView().keyReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameStates.state){
            case MENU:
                gamePanel.getGameView().getMenuView().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGameView().getPlayingView().keyPressed(e);
                break;
            case EDIT:
                gamePanel.getGameView().getEditingView().keyPressed(e);
                break;
            default:
                break;
        }
    }
}

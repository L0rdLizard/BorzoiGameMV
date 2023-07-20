package ru.maxim.borzoi.views;

import ru.maxim.borzoi.entities.Player;

import ru.maxim.borzoi.gameStates.Editing;
import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameStates.Menu;
import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.levels.LevelManager;
import ru.maxim.borzoi.utilz.LoadSave;


import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.gameMain.GamePanel;
import ru.maxim.borzoi.gameMain.GameWindow;

import java.awt.Graphics;

public class GameView implements Runnable{

    private Game game;
    private MenuView menuView;
    private EditingView editingView;


    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread renderThread;

    private PlayingView playingView;


    public GameView(Game game) {

        this.menuView = new MenuView(game);
        this.playingView = new PlayingView(game);
        this.editingView = new EditingView(game);
        gamePanel = new GamePanel(game, this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        this.game = game;

        startRenderLoop();
    }

    public void update(){
        switch (GameStates.state) {
            case MENU:
                menuView.update();
                break;
            case PLAYING:
                playingView.update();
                break;
            case EDIT:
                editingView.update();
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
//            if (game.getShouldRepaint())
            gamePanel.repaint();
        }

    }

    private void startRenderLoop() {
        renderThread = new Thread(this);
        renderThread.start();
    }


    public void render(Graphics g) {
        switch (GameStates.state) {
            case MENU:
                menuView.draw(g);
                break;
            case PLAYING:
                playingView.draw(g);
                break;
            case EDIT:
                editingView.draw(g);
                break;
            default:
                break;
        }
    }
    public PlayingView getPlayingView() {
        return playingView;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public EditingView getEditingView() {
        return editingView;
    }



}


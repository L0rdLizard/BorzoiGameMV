package ru.maxim.borzoi.views;

import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameStates.StateMethods;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
public class PlayingView implements StateMethods {

    private Game game;

    private PlayerView playerView;
    private EnemyManagerView enemyManagerView;
    private LevelView levelView;
    private ObjectManagerView objectManagerView;

    private GameOverOverlayView gameOverOverlayView;
    private LevelCompletedOverlayView levelCompletedOverlayView;

    private int xLvlOffset;
    private int leftBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    private int yLvlOffset;
    private int upBorder = (int)(0.6 * Game.GAME_HEIGHT);
    private int downBorder = (int)(0.4 * Game.GAME_HEIGHT);
    private int maxLvlOffsetY;

    private BufferedImage backgroundImg;
    private int bgWidth, bgHeight, fgWidth, fgHeight, ktWidth, ktHeight;
    public PlayingView(Game game) {
        this.game = game;
        playerView = new PlayerView(game.getPlaying());

        enemyManagerView = new EnemyManagerView(game.getPlaying());
        levelView = new LevelView(game.getPlaying());
        objectManagerView = new ObjectManagerView(game.getPlaying());
        levelCompletedOverlayView = new LevelCompletedOverlayView(game.getPlaying());
        gameOverOverlayView = new GameOverOverlayView(game.getPlaying());

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG_PLAYING);

    }

    @Override
    public void update() {
        if (game.getPlaying().getLvlCompleted()) {
            levelCompletedOverlayView.update();
        } else if (!game.getPlaying().getGameOver()){
            playerView.update();
        }

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        levelView.draw(g, game.getPlaying().getXLvlOffset(), game.getPlaying().getYLvlOffset());
        playerView.render(g, game.getPlaying().getXLvlOffset(), game.getPlaying().getYLvlOffset());
        enemyManagerView.draw(g, game.getPlaying().getXLvlOffset(), game.getPlaying().getYLvlOffset());
        objectManagerView.draw(g, game.getPlaying().getXLvlOffset(), game.getPlaying().getYLvlOffset());
        if (game.getPlaying().getGameOver()){
            gameOverOverlayView.draw(g);
        } else if (game.getPlaying().getLvlCompleted()){
            levelCompletedOverlayView.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!game.getPlaying().getGameOver()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                game.getPlaying().getPlayer().setAttack(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!game.getPlaying().getGameOver()) {
            if (game.getPlaying().getLvlCompleted())
                levelCompletedOverlayView.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!game.getPlaying().getGameOver()) {
            if (game.getPlaying().getLvlCompleted())
                levelCompletedOverlayView.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!game.getPlaying().getGameOver()) {
            if (game.getPlaying().getLvlCompleted())
                levelCompletedOverlayView.mouseMoved(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.getPlaying().getGameOver())
            gameOverOverlayView.keyPressed(e);
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    game.getPlaying().getPlayer().setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    game.getPlaying().getPlayer().setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    game.getPlaying().getPlayer().setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    GameStates.state = GameStates.MENU;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!game.getPlaying().getGameOver()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    game.getPlaying().getPlayer().setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    game.getPlaying().getPlayer().setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    game.getPlaying().getPlayer().setJump(false);
                    game.getPlaying().getPlayer().doubleJump = true;
                    game.getPlaying().getPlayer().jumps++;
                    break;
            }
        }
    }
}

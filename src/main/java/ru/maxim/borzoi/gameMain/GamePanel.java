package ru.maxim.borzoi.gameMain;

import ru.maxim.borzoi.inputs.KeyBoardInputs;
import ru.maxim.borzoi.inputs.MouseInputs;
import ru.maxim.borzoi.views.EditingView;
import ru.maxim.borzoi.views.GameView;

import javax.swing.*;
import java.awt.*;

import static ru.maxim.borzoi.gameMain.Game.GAME_HEIGHT;
import static ru.maxim.borzoi.gameMain.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private KeyBoardInputs keyboardInputs;
    private MouseInputs mouseInputs;
    private Game game;
    private GameView gameView;
    public GamePanel(Game game, GameView gameView){
        keyboardInputs = new KeyBoardInputs(this);
        mouseInputs = new MouseInputs(this);

        this.game = game;
        this.gameView = gameView;

//        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
//        setMinimumSize(size);
        setPreferredSize(size);
//        setMaximumSize(size);
//        System.out.println("size: " + GAME_WIDTH + "/"+ GAME_HEIGHT);
    }

    public void updateGame(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        gameView.render(g);
    }
    public Game getGame(){
        return game;
    }

    public GameView getGameView(){
        return gameView;
    }


}

package ru.maxim.borzoi.views;

import ru.maxim.borzoi.UI.MenuButton;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameStates.State;
import ru.maxim.borzoi.gameStates.StateMethods;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
public class MenuView extends State implements StateMethods {

    private BufferedImage backgroundImg, backgroundImgDark;
    private int menuX, menuY, menuWidth, menuHeight;
    private MenuButtonView[] buttonsView = new MenuButtonView[3];
    public MenuView(Game game) {
        super(game);
        loadBackground();
        loadButtonsView();
        update();
    }

    private void loadBackground() {
        backgroundImgDark = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (60 * Game.SCALE);
    }

    private void loadButtonsView() {
        buttonsView[0] = new MenuButtonView(Game.GAME_WIDTH / 2, (int) (150*Game.SCALE), 0);
        buttonsView[1] = new MenuButtonView(Game.GAME_WIDTH / 2, (int) (220*Game.SCALE), 1);
        buttonsView[2] = new MenuButtonView(Game.GAME_WIDTH / 2, (int) (290*Game.SCALE), 2);
    }

    @Override
    public void update() {
        for(MenuButtonView mb: buttonsView){
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgDark, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        for(MenuButtonView mb: buttonsView){
            mb.update();
            mb.draw(g);
        }
//        g.setColor(Color.black);
//        g.drawString("PITO ADVENTURE",game.GAME_WIDTH/2, 200);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButtonView mb: buttonsView){
            if (isIn(e,game.getMenu().getButtons()[mb.getRowIndex()])){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButtonView mb: buttonsView){
            if (isIn(e,game.getMenu().getButtons()[mb.getRowIndex()])){
                if(mb.isMousePressed()){
                    game.getMenu().getButtons()[mb.getRowIndex()].applyGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButtonView mb: buttonsView){
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButtonView mb: buttonsView){
            mb.setMouseOver(false);
        }
        for(MenuButtonView mb: buttonsView){
            if(isIn(e,game.getMenu().getButtons()[mb.getRowIndex()])){
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            GameStates.state = GameStates.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

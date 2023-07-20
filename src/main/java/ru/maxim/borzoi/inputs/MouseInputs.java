package ru.maxim.borzoi.inputs;

import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameMain.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    private boolean firstEdit = true, firstPlaying = true;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.state){
//            case MENU:
//                gamePanel.getGame().getMenu().mouseClicked(e);
//                break;
            case PLAYING:
                if (firstPlaying){
                    firstPlaying = false;
                    break;
                }
                gamePanel.getGameView().getPlayingView().mouseClicked(e);
                break;
            case EDIT:
                if (firstEdit){
                    firstEdit = false;
                    break;
                }
                gamePanel.getGameView().getEditingView().mouseClicked(e);
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.state){
            case MENU:
                gamePanel.getGameView().getMenuView().mousePressed(e);
                break;
            case PLAYING:
                if (firstPlaying){
                    firstPlaying = false;
                    break;
                }
                gamePanel.getGameView().getPlayingView().mousePressed(e);
                break;
            case OPTIONS:
                gamePanel.getGameView().getEditingView().mousePressed(e);
            case EDIT:
                if (firstEdit){
                    firstEdit = false;
                    break;
                }
                gamePanel.getGameView().getEditingView().mousePressed(e);
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.state){
            case MENU:
                gamePanel.getGameView().getMenuView().mouseReleased(e);
                break;
            case PLAYING:
                if (firstPlaying){
                    firstPlaying = false;
                    break;
                }
                gamePanel.getGameView().getPlayingView().mouseReleased(e);
                break;
            case OPTIONS:
                gamePanel.getGameView().getEditingView().mouseReleased(e);
            case EDIT:
                if (firstEdit){
                    firstEdit = false;
                    break;
                }
                gamePanel.getGameView().getEditingView().mouseReleased(e);
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.state) {
            case EDIT:
                if (firstEdit) {
                    firstEdit = false;
                    break;
                }
                gamePanel.getGameView().getEditingView().mouseDragged(e);

            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        gamePanel.setNosPos(e.getX(), e.getY()-40);
        switch (GameStates.state){
            case MENU:
                gamePanel.getGameView().getMenuView().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGameView().getPlayingView().mouseMoved(e);
                break;
            case EDIT:
                gamePanel.getGameView().getEditingView().mouseMoved(e);
            default:
                break;
        }
    }
}

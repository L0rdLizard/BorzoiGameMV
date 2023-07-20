package ru.maxim.borzoi.gameStates;

import ru.maxim.borzoi.UI.MenuButton;
import ru.maxim.borzoi.gameMain.Game;


public class Menu {
    private MenuButton[] buttons = new MenuButton[3];
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        loadButtons();
    }



    public void update() {
        for(MenuButton mb: buttons){
            mb.update();
        }
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150*Game.SCALE), 0, GameStates.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220*Game.SCALE), 1, GameStates.EDIT);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290*Game.SCALE), 2, GameStates.QUIT);
    }

    public MenuButton[] getButtons() {
        return buttons;
    }


}

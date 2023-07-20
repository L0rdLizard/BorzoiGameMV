package ru.maxim.borzoi.gameMain;
import ru.maxim.borzoi.views.GameView;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
//        GameView(game);
        GameView gameView = new GameView(game);
    }
}
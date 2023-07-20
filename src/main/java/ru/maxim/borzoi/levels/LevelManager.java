package ru.maxim.borzoi.levels;

import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;
    public LevelManager(Game game){
        this.game = game;
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel(){
        lvlIndex++;
        if (lvlIndex >= levels.size()){
            lvlIndex = 0;
            System.out.println("No more levels");
            GameStates.state = GameStates.MENU;
        }
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
//        game.getPlaying().getObjectManager().loadObjects(newLevel);
        game.getPlaying().setMaxLvlOffsetX(newLevel.getLvlOffsetX());
        game.getPlaying().setMaxLvlOffsetY(newLevel.getLvlOffsetY());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    public void reloadCurrentLevel() {
        int lvlIndex = 0;
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    public void buildAllLevels() {
        int index = 0;

        System.out.println("Building all levels");
        BufferedImage[] allLevel = LoadSave.GetAllLevels();

        for (BufferedImage img : allLevel){
            levels.add(new Level(img));
            index++;
        }
    }

    public void update(){

    }
    public Level getCurrentLevel(){
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels(){
        return levels.size();
    }
    public ArrayList<Level> getLevels(){
        return levels;
    }

    public int getLevelIndex() {
        return lvlIndex;
    }

    public int getLvlIndex() {
        return lvlIndex;
    }
}

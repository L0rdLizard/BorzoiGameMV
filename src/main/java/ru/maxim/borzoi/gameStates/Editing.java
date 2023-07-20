package ru.maxim.borzoi.gameStates;

import ru.maxim.borzoi.UI.MenuButton;
import ru.maxim.borzoi.levels.Level;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.utilz.HelpMethods;
import ru.maxim.borzoi.utilz.HelpMethods.*;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Editing {

    private MenuButton[] editButtons = new MenuButton[6];
    private BufferedImage[] allLevel = new BufferedImage[4];
    private Game game;
    private int[][] lvlData;


    private int lvlIndex = 0;
    public Editing(Game game) {
        this.game = game;
        setCurrentEditingLevel(lvlIndex);
        getAllLvlData();

    }

    public void update() {

    }

    private void getAllLvlData(){
        allLevel = LoadSave.GetAllLevels();
    }

    public void setCurrentEditingLevel(int index){

        this.lvlIndex = index;
        getAllLvlData();

        lvlData = game.getPlaying().getLevelManager().getLevels().get(index).getLvlData();

    }


    public void changePixel(int xTile, int yTile, int[][] lvlData, int index){
        lvlData[yTile][xTile] = index;
        if (index == 200){
            game.getPlaying().getLevelManager().getLevels().get(lvlIndex).addBall(xTile * 64, yTile * 64);
        }
        if (index == 250){
            game.getPlaying().getLevelManager().getLevels().get(lvlIndex).addSpike(xTile * 64, yTile * 64);
        }
//        System.out.println("x = " + yTile + " y = " + xTile);
        saveLvlToFile();
    }

    private void saveLvlToFile(){
        BufferedImage temp = HelpMethods.LvlDataToImage(lvlData);
        saveLocal();
        LoadSave.SaveLevel(temp, lvlIndex);
    }

    private void saveLocal() {
        game.getPlaying().getLevelManager().getLevels().get(lvlIndex).setLvlData(lvlData);
        game.getPlaying().getPlayer().loadLvlData(lvlData);
    }

    public int[][] getLvlData() {
        return lvlData;
    }



}

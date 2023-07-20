package ru.maxim.borzoi.views;

import ru.maxim.borzoi.gameStates.GameStates;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
public class LevelView {

    private Playing playing;
    private BufferedImage[] levelSprite;

    public LevelView(Playing playing) {
        this.playing = playing;
        importOutsideSprite();
    }

    private void importOutsideSprite() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++){
            for (int i = 0; i < 12; i++){
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
            }
        }
    }


    public void draw(Graphics g, int xLvlOffset, int yLvlOffset){
        for (int j = 0; j < playing.getLevelManager().getLevels().get(playing.getLevelManager().getLvlIndex()).getLvlData().length; j++){
            for (int i = 0; i < playing.getLevelManager().getLevels().get(playing.getLevelManager().getLvlIndex()).getLvlData()[0].length; i++){
                int index = playing.getLevelManager().getLevels().get(playing.getLevelManager().getLvlIndex()).getSpriteIndex(i, j);
                if (index >= 48)
                    index = 0;
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - xLvlOffset, Game.TILES_SIZE * j - yLvlOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

}

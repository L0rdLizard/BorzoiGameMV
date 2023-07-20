package ru.maxim.borzoi.views;

import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.levels.Level;
import ru.maxim.borzoi.objects.Coin;
import ru.maxim.borzoi.objects.Spike;
import ru.maxim.borzoi.utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ru.maxim.borzoi.utilz.Constants.ObjectConstants.*;
import static ru.maxim.borzoi.utilz.Constants.ObjectConstants.COIN_HEIGHT;

public class ObjectManagerView {

    private Playing playing;
    private BufferedImage[][] coinImgs;
    private BufferedImage spikeImg;

    public ObjectManagerView(Playing playing) {
        this.playing = playing;
        loadImgs();
    }



    private void loadImgs() {
        BufferedImage coinSprite = LoadSave.GetSpriteAtlas(LoadSave.COIN_ATLAS);
        coinImgs = new BufferedImage[1][4];

        for (int j = 0; j < coinImgs.length; j++)
            for (int i = 0; i < coinImgs[j].length; i++)
                coinImgs[j][i] = coinSprite.getSubimage(16 * i, 16 * j, 16, 16);

        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.SPIKES);
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
//        drawPotions(g, xLvlOffset);
//        drawContainers(g, xLvlOffset);
        drawCoins(g, xLvlOffset,  yLvlOffset);
        drawTraps(g, xLvlOffset, yLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset, int yLvlOffset) {
        if (playing.getObjectManager().getSpikes() == null)
            return;
        for (Spike s : playing.getObjectManager().getSpikes())
            g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset() - yLvlOffset), SPIKE_WIDTH, SPIKE_HEIGHT, null);

    }

    private void drawCoins(Graphics g, int xLvlOffset, int yLvlOffset) {
//        if (coins == null)
//            return;
        for (Coin c : playing.getObjectManager().getCoins())
            if (c.isActive()) {
                int type = 0;
                if (c.getObjType() == COIN2)
                    type = 1;
                if (c.getObjType() == COIN3)
                    type = 2;
                type = 0;
                g.drawImage(coinImgs[type][c.getAniIndex()], (int) (c.getHitbox().x - c.getxDrawOffset() - xLvlOffset), (int) (c.getHitbox().y - c.getyDrawOffset() -  yLvlOffset), COIN_WIDTH * 2,
                        COIN_HEIGHT * 2, null);
            }
    }
}

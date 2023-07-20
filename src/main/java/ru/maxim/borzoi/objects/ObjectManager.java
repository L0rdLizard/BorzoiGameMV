package ru.maxim.borzoi.objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ru.maxim.borzoi.entities.Player;
import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.levels.Level;
import ru.maxim.borzoi.utilz.LoadSave;
import static ru.maxim.borzoi.utilz.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] coinImgs;
    private BufferedImage spikeImg;
    private ArrayList<Coin> coins;
    private ArrayList<Spike> spikes;

    public ObjectManager(Playing playing) {
        this.playing = playing;

        coins = new ArrayList<>();
//        coins.add(new Coin(200, 200, COIN));
    }

    public void checkSpikesTouched(Player p) {
        if (spikes == null)
            return;
        for (Spike s : spikes)
            if (s.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
//        if (coins == null)
//            return;
        for (Coin c : coins)
            if (c.isActive()) {
                if (hitbox.intersects(c.getHitbox())) {
                    c.setActive(false);
                    applyEffectToPlayer(c);
                }
            }
    }

    public void applyEffectToPlayer(Coin c) {
        if (c.getObjType() == COIN)
            playing.increaseCoin(1);
        else if (c.getObjType() == COIN2)
            playing.increaseCoin(2);
        else if (c.getObjType() == COIN3)
            playing.increaseCoin(3);
    }

    public void addCoin(int x, int y, int type){
        coins.add(new Coin(x, y, type));
    }

//    public void checkObjectHit(Rectangle2D.Float attackbox) {
//        for (GameContainer gc : containers)
//            if (gc.isActive()) {
//                if (gc.getHitbox().intersects(attackbox)) {
//                    gc.setAnimation(true);
//                    int type = 0;
//                    if (gc.getObjType() == BARREL)
//                        type = 1;
//                    potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
//                    return;
//                }
//            }
//    }

    public void loadObjects(Level newLevel) {
        coins = new ArrayList<>(newLevel.getCoins());
        spikes = newLevel.getSpikes();
    }



    public void update() {
//        if (coins == null)
//            return;
        for (Coin c : coins)
            if (c.isActive())
                c.update();
    }



    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());

        for (Coin c : coins)
            c.reset();
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

}
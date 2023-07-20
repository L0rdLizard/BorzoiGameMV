package ru.maxim.borzoi.gameStates;

import ru.maxim.borzoi.UI.GameOverOverlay;
import ru.maxim.borzoi.UI.LevelCompletedOverlay;
import ru.maxim.borzoi.entities.EnemyManager;
import ru.maxim.borzoi.entities.Player;
import ru.maxim.borzoi.levels.LevelManager;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.objects.ObjectManager;
import ru.maxim.borzoi.utilz.LoadSave;


import java.awt.geom.Rectangle2D;


public class Playing extends State{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;

    private int xLvlOffset;
    private int leftBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    private int yLvlOffset;
    private int upBorder = (int)(0.6 * Game.GAME_HEIGHT);
    private int downBorder = (int)(0.4 * Game.GAME_HEIGHT);
    private int maxLvlOffsetY;

    private boolean gameOver;
    private boolean lvlCompleted = false;


    public Playing(Game game){
        super(game);
        initClasses();


        calcLvlOffset();
        loadStartLevel();
    }

    public void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);

        player = new Player(200, 300, (int) (80 * Game.SCALE), (int) (64 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        gameOverOverlay = new GameOverOverlay();
        levelCompletedOverlay = new LevelCompletedOverlay();
    }

    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }
    public void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    public void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffsetX();
        maxLvlOffsetY = levelManager.getCurrentLevel().getLvlOffsetY();
    }

    public void update() {
         if (lvlCompleted) {
            levelCompletedOverlay.update();
        } else if (!gameOver) {
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            checkCloseToBorder();
            checkCloseToRoof();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder){
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder){
            xLvlOffset += diff - leftBorder;
        }

        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0){
            xLvlOffset = 0;
        }
    }

    private void checkCloseToRoof() {
        int playerY = (int) player.getHitbox().y;
        int diff = playerY - yLvlOffset;

        if (diff > upBorder){
            yLvlOffset += diff - upBorder;
        } else if (diff < downBorder){
            yLvlOffset += diff - downBorder;
        }

        if(yLvlOffset > maxLvlOffsetY)
            yLvlOffset = maxLvlOffsetY;
        else if (yLvlOffset < 0){
            yLvlOffset = 0;
        }
    }



    public void createCoin(int x, int y, int type){
        objectManager.addCoin(x, y, type);
    }

    public void increaseCoin(int coins){
        int playerCoins;
        // save player coins to bin file in folder gameData
        playerCoins = LoadSave.LoadPlayerCoins();
        playerCoins += coins;
        LoadSave.SavePlayerCoins(playerCoins);
        System.out.println("Player coins: " + playerCoins);
    }

    public int getCoins(){
        return LoadSave.LoadPlayerCoins();
    }

    public void resetAll() {
        gameOver = false;
//        paused = false;
        lvlCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkCoinTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }
    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }



    public void setLevelCompleted(boolean levelCompleted){
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffsetX(int lvlOffsetX){
        this.maxLvlOffsetX = lvlOffsetX;
    }
    public void setMaxLvlOffsetY(int lvlOffsetY){
        this.maxLvlOffsetY = lvlOffsetY;
    }

    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    public Player getPlayer(){
        return player;
    }
    public EnemyManager getEnemyManager(){
        return enemyManager;
    }
    public ObjectManager getObjectManager() {
        return objectManager;
    }
    public LevelManager getLevelManager(){
        return levelManager;
    }


    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public int getXLvlOffset() {
        return xLvlOffset;
    }
    public int getYLvlOffset() {
        return yLvlOffset;
    }

    public boolean getGameOver() {
        return gameOver;
    }


    public GameOverOverlay getGameOverOverlay() {
        return gameOverOverlay;
    }

    public LevelCompletedOverlay getLevelCompletedOverlay() {
        return levelCompletedOverlay;
    }



    public boolean getLvlCompleted() {
        return lvlCompleted;
    }

}

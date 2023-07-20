package ru.maxim.borzoi.entities;

import ru.maxim.borzoi.gameStates.Playing;
import ru.maxim.borzoi.gameMain.Game;
import ru.maxim.borzoi.utilz.LoadSave;


import java.awt.*;
import java.awt.geom.Rectangle2D;

// import point


import static ru.maxim.borzoi.utilz.HelpMethods.*;
import static ru.maxim.borzoi.utilz.Constants.PlayerConstants.*;

public class Player extends Entity{

    private int animTick, animIndex, animSpeed = 28;
    private int playerAction = IDLE;
    boolean left, right, up, down, jump;
    private boolean moving = false, attacking = false;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] lvlData;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private Playing playing;

    // Jumping
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    // Jump / Gravity

    private float xDrawOffset = 36 * Game.SCALE;
    private float yDrawOffset = 29 * Game.SCALE;
    public boolean doubleJump = false;
    public int jumps = 0;

    // HP bar
    private int hpBarWidth = (int) (96 * Game.SCALE);
    private int hpBarHeight = (int) (32 * Game.SCALE);

    private int coinBarWidth = (int) (38 * Game.SCALE);
    private int coinBarHeight = (int) (38 * Game.SCALE);
    private int hpBarX = (int) (10 * Game.SCALE);
    private int hpBarY = (int) (10 * Game.SCALE);
    private int maxHealth = 3;
    private int currentHealth = maxHealth;

    // Attack hitbox
    private Rectangle2D.Float attackBox;


    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        initHitbox(x, y, (int) (30*Game.SCALE), (int) (30*Game.SCALE));
        initAttackBox();
    }

    public void setSpawn(Point spawn){
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(30 * Game.SCALE), (int)(40 * Game.SCALE));
    }

    public void update() {

        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();

        updatePos();
        if (moving) {
            checkCoinTouched();
            checkSpikesTouched();
        }
        if (attacking)
            checkAttack();

        updateAnimationTick();
        setAnimation();
    }

    public void checkSpikesTouched() {
        playing.checkSpikesTouched(this);

    }

    private void checkCoinTouched() {
        playing.checkCoinTouched(hitbox);
    }

    private void checkAttack() {
        if (attackChecked || animIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);

    }

    private void updateAttackBox() {
        if (right){
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 5);
        } else if (left){
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 5);
        }
        attackBox.y = hitbox.y - (Game.SCALE * 10);
    }


    private void updateAnimationTick() {
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(playerAction)){
                animIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = DROP;
        }

        if (attacking) {
            playerAction = ATTACK;
            if (startAni != ATTACK){
                animIndex = 1;
                animTick = 0;
                return;
            }
        }

        if (startAni != playerAction)
            resetAnimTick();
    }

    private void resetAnimTick() {
        animTick = 0;
        animIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (!inAir && right && left){
            return;
        }

        if (jump)
            jump();
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = 0;
            flipW = 1;

        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = width + 44;
            flipW = -1;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosNextToBarrier(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
//        if (inAir)
//            return;
        if (inAir)
            if (doubleJump && jumps < 2) {
                airSpeed = jumpSpeed;
                doubleJump = false;
                return;
            }else
                return;
        inAir = true;
        doubleJump = true;
        airSpeed = jumpSpeed;
    }

    public void kill() {
        currentHealth = 0;
    }
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
        jumps = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value){
        currentHealth += value;
        if (currentHealth <= 0){
            currentHealth = 0;
            // gameOver();
        }else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }


    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }
    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public void setAttack(boolean attacking){
        this.attacking = attacking;
    }
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getPlayerAction() {
        return playerAction;
    }

    public int getAnimIndex() {
        return animIndex;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFlipX() {
        return flipX;
    }

    public int getFlipW() {
        return flipW;
    }

    public boolean getAttacking() {
        return attacking;
    }

}

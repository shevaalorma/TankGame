package TankGame04;

import java.util.Vector;

public class Tank {
    private int x;
    private int y;
    boolean isLive = true;
    Vector<Shoot> shoots = new Vector<>();
    private int direct = 0;
    private int speed = 1;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveUp() {
        this.direct = 0;
        this.y -= speed;
    }

    public void moveDown() {
        this.direct = 1;
        this.y += speed;
    }

    public void moveLeft() {
        this.direct = 2;
        this.x -= speed;
    }

    public void moveRight() {
        this.direct = 3;
        this.x += speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
}

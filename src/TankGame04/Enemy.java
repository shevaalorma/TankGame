package TankGame04;

import java.util.Vector;

public class Enemy extends Tank implements Runnable {
    Vector<Shoot> shoots = new Vector<>();
    boolean isLive = true;

    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (isLive) {
            if (shoots.size() < 3) {
                Shoot shoot = null;
                switch (getDirect()) {
                    case 0:
                        shoot = new Shoot(getX() + 20, getY(), getDirect());
                        break;
                    case 1:
                        shoot = new Shoot(getX() + 20, getY() + 60, getDirect());
                        break;
                    case 2:
                        shoot = new Shoot(getX(), getY() + 20, getDirect());
                        break;
                    case 3:
                        shoot = new Shoot(getX() + 60, getY() + 20, getDirect());
                        break;
                }

                if (shoot != null) {
                    shoots.add(shoot);
                    new Thread(shoot).start();
                }
            }
            int enemy_Direct = (int) (Math.random() * 4);
            switch (enemy_Direct) {
                case 0:
                    for (int i = 0; i < 25; i++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (getY() > 0) {
                            moveUp();
                        } else {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 25; i++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (getY() + 60 < 600) {
                            moveDown();
                        } else {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 25; i++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (getX() > 0) {
                            moveLeft();
                        } else {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 25; i++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (getX() + 60 < 800) {
                            moveRight();
                        } else {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }

        }
    }
}






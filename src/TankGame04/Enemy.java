package TankGame04;

import java.util.Vector;

public class Enemy extends Tank implements Runnable{
    Vector<Shoot> shoots = new Vector<>();
    boolean isLive = true;
    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true){
            int enemy_Direct = (int)(Math.random() * 4);
            switch (enemy_Direct){
                case 0:
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(getY()>0){
                        for (int i = 0; i < 30; i++) {
                            moveUp();
                        }
                    }
                    break;
                case 1:
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 30; i++) {
                        if(getY()+60 >0){
                            moveDown();
                        }
                    }
                    break;
                case 2:
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 30; i++) {
                        if(getX()>0){
                            moveLeft();
                        }
                    }
                    break;
                case 3:
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 30; i++) {
                        if(getX()+60>0){
                            moveRight();
                        }
                    }
                    break;
            }
            if(!isLive){
                break;
            }
        }


    }
}

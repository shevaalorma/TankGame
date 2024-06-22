package TankGame04;

import java.time.LocalDateTime;


public class Shoot implements Runnable {

    int x;
    int y;
    int direct;
    int speed = 20;
    boolean isLive = true;

    public Shoot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }


    @Override
    public  void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 更新子弹坐标
            switch (direct) {
                case 0: y -= speed; break;
                case 1: y += speed; break;
                case 2: x -= speed; break;
                case 3: x += speed; break;
            }


            if(x>800||x<0||y>600||y<0||this.isLive==false){
                System.out.println("子弹销毁     "+ LocalDateTime.now());
                isLive = false;

                break;
            }
//            System.out.println("当前坐标是x-"+x+"   "+"y-"+y);
        }
    }
}

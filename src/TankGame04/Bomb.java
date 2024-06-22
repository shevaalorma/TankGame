package TankGame04;

public class Bomb implements Runnable {
    int x;
    int y;
    boolean isLive = true;
    int life = 9;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        while (isLive) {
            try {
                Thread.sleep(10); // 控制爆炸效果的播放速度
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            life--;
            if (life <= 0) {
                isLive = false; // 爆炸效果播放完毕
            }
        }
    }
}

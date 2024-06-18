package TankGame04;

public class Bomb implements Runnable {
    int x;
    int y;
    Boolean isLive = true;
    int life = 9;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            life--;
            if (this.life <= 0) {
                System.out.println("爆炸完了");
                break;
            }
        }
    }
}

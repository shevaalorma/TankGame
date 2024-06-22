package TankGame04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class HspTankGame extends JFrame {
    MyPanel mp = null;

    public HspTankGame() {
        mp = new MyPanel();
        new Thread(mp).start();
        this.add(mp);
        this.setVisible(true);
        this.setSize(800, 600);
        this.addKeyListener(mp);
//        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new HspTankGame();
    }
}

class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero = null;
    Vector<Hero> heros = new Vector<>();
    Vector<Enemy> enemy_Tanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int enemy_size = 3;
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel() {
        heros.add(new Hero(500, 500));
        hero = heros.get(0);
        for (int i = 0; i < enemy_size; i++) {
            Enemy enemyTank = new Enemy(100 * (1 + i), 0);
            enemyTank.setDirect(1);
            new Thread(enemyTank).start();
            enemy_Tanks.add(enemyTank);

            image1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_1.gif"));
            image2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_2.gif"));
            image3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_3.gif"));
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 800, 600);

        for (int i = 0; i < heros.size(); i++) {
            Hero heroTank = heros.get(i);
            drawTank(heroTank.getX(), heroTank.getY(), g, 0, heroTank.getDirect());
            for (int j = 0; j < heroTank.shoots.size(); j++) {
                Shoot shoot = heroTank.shoots.get(j);
                if (shoot.isLive) {
                    drawShoot(shoot.x, shoot.y, g);
//                    System.out.println("画子弹" + shoot.x + " | " + shoot.y);
                } else {
                    heroTank.shoots.remove(j);
                }
            }
        }


        for (int i = 0; i < enemy_Tanks.size(); i++) {
            Enemy enemyTank = enemy_Tanks.get(i);
            drawTank(enemyTank.getX(), enemyTank.getY(), g, 1, enemyTank.getDirect());
            for (int j = 0; j < enemyTank.shoots.size(); j++) {
                Shoot shoot = enemyTank.shoots.get(j);
                if (shoot.isLive) {
                    drawShoot(shoot.x, shoot.y, g);
//                    System.out.println("画子弹" + shoot.x + " | " + shoot.y);
                } else {
                    enemyTank.shoots.remove(j);
                }
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            drawBomb(bombs.get(i), g);
        }
    }


    public void drawBomb(Bomb bomb, Graphics g) {
        if (bomb.life > 6) {
            g.drawImage(image1, bomb.x, bomb.y, 60, 60, null);
        } else if (bomb.life > 3) {
            g.drawImage(image2, bomb.x, bomb.y, 60, 60, null);
        } else if (bomb.life > 0) {
            g.drawImage(image3, bomb.x, bomb.y, 60, 60, null);
        } else {
            bomb.isLive = false;
            bombs.remove(bomb);
        }
    }

    public boolean hit(Vector<? extends Tank> tanks, Vector<Shoot> shoots)  {
        boolean hit = false;
        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            for (int j = 0; j < shoots.size(); j++) {
                Shoot shoot = shoots.get(j);
                if (tank.isLive && shoot.isLive) {
                    switch (tank.getDirect()) {
                        case 0: // Up
                        case 1: // Down
                            hit = shoot.x > tank.getX() && shoot.x < tank.getX() + 40
                                    && shoot.y > tank.getY() && shoot.y < tank.getY() + 60;
                            break;
                        case 2: // Left
                        case 3: // Right
                            hit = shoot.x > tank.getX() && shoot.x < tank.getX() + 60
                                    && shoot.y > tank.getY() && shoot.y < tank.getY() + 40;
                            break;
                    }
                    if (hit) {
                        tank.isLive = false;
                        tanks.remove(tank);
                        shoot.isLive = false;
                        shoots.remove(shoot);
                        i--; // Adjust index after removal
                        break;
                    }
                }
            }
        }
        return hit;
    }

    public void hitTank(){
        for (int i = 0; i < enemy_Tanks.size(); i++) {
            Enemy enemyTank = enemy_Tanks.get(i);
            for (int j = 0; j < heros.size(); j++) {
                Hero hero = heros.get(j);
                if(hit(enemy_Tanks, hero.shoots)){
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    new Thread(bomb).start();
                    this.repaint();
                }

                if(hit(heros,enemyTank.shoots)){
                    Bomb bomb = new Bomb(hero.getX(), hero.getY());
                    bombs.add(bomb);
                    new Thread(bomb).start();
                    this.repaint();
                }
            }
        }



    }

    public void drawTank(int x, int y, Graphics g, int type, int direct) {
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }
        switch (direct) {
            case 0://方向上
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1://方向下
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 2://方向左
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            case 3://方向右
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
        }


    }

    public void drawShoot(int x, int y, Graphics g) {
        g.setColor(Color.RED);
        g.draw3DRect(x, y, 1, 1, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.moveUp();
            hero.setDirect(0);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.moveDown();
            hero.setDirect(1);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.moveLeft();
            hero.setDirect(2);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.moveRight();
            hero.setDirect(3);
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            for (int i = 0; i < hero.shoots.size(); i++) {
                if(!hero.shoots.get(i).isLive){
                    hero.shoots.remove(i);
                    i--;
                }
            }
            if (hero.shoots.size() < 5) {
                hero.shootEnemy();
            }
            this.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hitTank();
            this.repaint();
        }
    }

}

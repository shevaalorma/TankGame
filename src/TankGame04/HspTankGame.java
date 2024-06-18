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
        this.setSize(1440, 900);
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
    Vector<Enemy> enemy_Tanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int enemy_size = 3;
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel() {
        hero = new Hero(100, 100);
        for (int i = 0; i < enemy_size; i++) {
            Enemy enemyTank = new Enemy(100 * (1 + i), 0);
            enemyTank.setDirect(1);
            new Thread(enemyTank).start();
            Shoot shoot = new Shoot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            enemyTank.shoots.add(shoot);
            new Thread(shoot).start();
            enemy_Tanks.add(enemyTank);
//            // 打印资源文件路径
//            System.out.println(getClass().getResource("/bomb_1.gif"));
//            System.out.println(getClass().getResource("/bomb_2.gif"));
//            System.out.println(getClass().getResource("/bomb_3.gif"));

            image1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_1.gif"));
            image2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_2.gif"));
            image3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_3.gif"));
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1440, 900);
        drawTank(hero.getX(), hero.getY(), g, 0, hero.getDirect());
        if (hero.shoot != null && hero.shoot.isLive != false) {
//           System.out.println("画子弹" + hero.shoot.x + " | " + hero.shoot.y);
            drawShoot(hero.shoot.x, hero.shoot.y, g);
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
            this.repaint();
        }
    }


    public void drawBomb(Bomb bomb, Graphics g) {
        if (bomb.life > 6) {
            g.drawImage(image1, bomb.x, bomb.y, 60, 60, null);
        } else if (bomb.life > 3) {
            g.drawImage(image2, bomb.x, bomb.y, 60, 60, null);
        } else if (bomb.life > 0){
            g.drawImage(image3, bomb.x, bomb.y, 60, 60, null);
        }else {
            bomb.isLive =false;
            bombs.remove(bomb);
        }
    }

    public void hitTank() {
        if (hero.shoot != null && hero.shoot.isLive) {
            for (int i = enemy_Tanks.size() - 1; i >= 0; i--) {
                Enemy enemyTank = enemy_Tanks.get(i);
                //敌方坦克活着， 英雄子弹穿过敌方坦克坐标
                if (enemyTank.isLive &&
                        hero.shoot.x > enemyTank.getX() && hero.shoot.x < enemyTank.getX() + 40 &&
                        hero.shoot.y > enemyTank.getY() && hero.shoot.y < enemyTank.getY() + 60) {
                    enemyTank.isLive = false;
                    hero.shoot.isLive = false;
                    for (int j = enemyTank.shoots.size() - 1; j >= 0; j--) {
                        if (enemyTank.shoots.get(j) != null) {
                            enemyTank.shoots.get(j).isLive = false;
                            enemyTank.shoots.remove(j);
                        }
                    }

                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    new Thread(bomb).start();
                    enemy_Tanks.remove(i); // 移除已击中的敌人
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
            hero.shootEnemy();

        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            hitTank();
            this.repaint();
        }

    }
}

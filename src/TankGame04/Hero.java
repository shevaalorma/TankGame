package TankGame04;

public class Hero extends Tank {

    public Hero(int x, int y) {
        super(x, y);
    }

    Shoot shoot = null;

    /**
    确定子弹初始位置在炮筒上
     */
    public void shootEnemy() {
        switch (getDirect()) {
            case 0:
                shoot = new Shoot(getX() + 20, getY(), 0);
                break;
            case 1:
                shoot = new Shoot(getX() + 20, getY() + 60, 1);
                break;
            case 2:
                shoot = new Shoot(getX(), getY()+20, 2);
                break;
            case 3:
                shoot = new Shoot(getX() + 60, getY() + 20, 3);
                break;
        }
        new Thread(shoot).start();
        System.out.println("当前坐标是X:"+ getX()+" "+"Y:" + getY());
    }
}

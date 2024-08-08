package com.chz.TankGame3;

/**
 * @Author chz
 * @Date 2024/5/29 20:14
 * @注释   坦克大战第三版
 */
@SuppressWarnings("all")
public class Tank {//用来存储坦克的位置信息
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private int direct = 0;//坦克方向, 0表示向上, 1表示向右, 2表示向下, 3表示向左
    private int speed = 2;//坦克移动的速度
    boolean isLive = true;//坦克是否存活

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
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

    //移动
    public void setY(int y) {
        this.y = y;
    }
    public void moveup(){//向上移动
        y-=speed;
    }
    public void moveDown(){//向下移动
        y+=speed;
    }
    public void moveLeft(){//向左移动
        x-=speed;
    }
    public void moveRight(){//向右移动
        x+=speed;
    }
}

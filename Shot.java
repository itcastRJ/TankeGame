package com.chz.TankGame3;

/**
 * @Author chz
 * @Date 2024/6/20 13:05
 * @说明 ：射击炮弹
 */

public class Shot implements Runnable {//炮弹
    /*
    @param x 炮弹的x坐标
    @param y 炮弹的y坐标
    @param direct 炮弹的朝向
    @param speed 炮弹的速度
     */
int x;
int y;
int direct;
int speed=5;
boolean isLive=true;//炮弹是否存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
@SuppressWarnings({"all"})
    @Override
    public void run() {
        while(isLive){
            try {//子弹间隔射出
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (direct){
                case 0://向上
                    y-=speed;
                    break;
                case 1://向右
                    x+=speed;
                    break;
                case 2://向左
                    x-=speed;
                    break;
                case 3://向下
                    y+=speed;
                    break;
            }
            if(!(x>=0&&x<=1000&&y>=0&&y<=750)){//炮弹超出范围,进行销毁
                 isLive=false;//炮弹死亡
                break;
            }
        }
    }
}

package com.chz.TankGame3;

/**
 * @Author chz
 * @Date 2024/6/23 12:48
 * @说明 ：爆炸类
 */

public  class Bomb {
    int x,y;//爆炸的坐标
     int life=9;//爆炸的生命周期
     boolean isLive=true;//爆炸状态是否存活
    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @SuppressWarnings("all")
    /** @说明 lifedown方法 随着爆炸的生命周期减少，爆炸的样式也随之改变*/
public void lifeDown(){
    if (life>0){
        life--;
    }else {
        isLive=false;
    }
}
}

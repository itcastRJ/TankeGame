package com.chz.TankGame3;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.Vector;

/**
 * @Author chz
 * @Date 2024/5/29 20:17
 * @注释  自己的坦克的位置信息
 */
@SuppressWarnings("all")
public class Hero extends Tank {


    //定义一个射击对象，表示一个线程
    Shot shot = null;
    //利用集合，发射多颗子弹
    static Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }
    /**@param shotEnemyTank 射击方法*/
    // 创建一个Shot对象，根据当前Hero对象的位置和方向来创建Shot
    public  void shotEnemyTank() {

            if (shots.size()<5) {
                    switch (getDirect()) {//根据当前Hero对象的方向来创建Shot,决定Shot坐标
                        case 0://上
                            shot = new Shot(getX() + 20, getY(), 0);
                            break;
                        case 1://右
                            shot = new Shot(getX() + 60, getY() + 20, 1);
                            break;
                        case 2://左
                            shot = new Shot(getX(), getY() + 20, 2);
                            break;
                        case 3://下
                            shot = new Shot(getX() + 20, getY() + 60, 3);
                            break;
                    }
            }
                //将创建的shot对象放入到shots集合
        if (shot != null) {
            shots.add(shot);
            //启动射击线程
            new Thread(shot).start();
        }
    }



}

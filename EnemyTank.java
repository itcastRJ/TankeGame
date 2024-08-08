package com.chz.TankGame3;

import java.util.Vector;

/**
 * @Author chz
 * @Date 2024/6/4 21:22
 * @说明 ： 敌方坦克
 */
@SuppressWarnings("all")
public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();//在敌方坦克类中，创建一个集合，用于存储所有的敌人坦克的开火炮弹
    //创建一个集合，用于存储所有的敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
     boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    /**@ 说明 setEnemyTanks方法，方便将Mypanel的成员Vector<EnemyTank> enemyTanks = new Vector<>();
     * 设置到EnemyTank的成员enemyTanks
     */
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    /** @说明 isTouchEnemyTank方法，判断是否和hero坦克以及友方重叠或者发生碰撞，重叠或者发生碰撞则结束游戏
     */

    public boolean isTouchEnemyTank(){
        //判断当前敌人坦克（this）的方向
       switch (this.getDirect()){
            case 0://上
                //让当前的坦克的坐标和Vector<EnemyTank> enemyTanks中的坦克的坐标进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector<EnemyTank> enemyTanks中取出一个坦克
                    EnemyTank enemy = enemyTanks.get(i);
                    //判断当前坦克是否是自身坦克
                    if(enemy != this){
                        //对敌人坦克方向进行判断
                        if (enemy.getDirect()==0 || enemy.getDirect()==3){//上下
                            if (this.getX()>=enemy.getX()
                                    && this.getX()<=enemy.getX()+40
                                    && this.getY()>=enemy.getY()
                                    && this.getY()<=enemy.getY()+60){
                                return true;
                            }
                            if(this.getX()+40 >= enemy.getX()
                                    && this.getX()+40 <= enemy.getX()+40
                                    && this.getY()>=enemy.getY()
                                    && this.getY()<=enemy.getY()+60
                            ){
                                return true;}
                        }
                        if(enemy.getDirect()==1 || enemy.getDirect()==2){//左右
                            if (this.getX()>=enemy.getX()
                                    && this.getX()<=enemy.getX()+60
                                    && this.getY()>=enemy.getY()
                                    && this.getY()<=enemy.getY()+40){
                                return true;
                            }
                            if (this.getX() +40 >= enemy.getX()
                                    && this.getX()+40<= enemy.getX()+60
                                    && this.getY() <=enemy.getY()+40
                                    && this.getY() >=enemy.getY()) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://右
                //让当前的坦克的坐标和Vector<EnemyTank> enemyTanks中的坦克的坐标进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector<EnemyTank> enemyTanks中取出一个坦克
                    EnemyTank enemy = enemyTanks.get(i);
                    //判断当前坦克是否是自身坦克
                    if(enemy != this){
                        //对敌人坦克方向进行判断
                        if (enemy.getDirect()==0 || enemy.getDirect()==3){//上下
                            if (this.getX()+60>=enemy.getX()
                                    && this.getX()+60<=enemy.getX()+40
                                    && this.getY()>=enemy.getY()
                                    && this.getY()<=enemy.getY()+60){
                                return true;
                            }
                            if(this.getX()+60 >= enemy.getX()
                                    && this.getX()+60 <= enemy.getX()+40
                                    && this.getY()+40>=enemy.getY()
                                    && this.getY()+40<=enemy.getY()+60){
                                return true;}
                        }
                        if(enemy.getDirect()==1 || enemy.getDirect()==2){//左右
                            if (this.getX()+60>=enemy.getX()
                                    && this.getX()+60<=enemy.getX()+60
                                    && this.getY()>=enemy.getY()
                                    && this.getY()<=enemy.getY()+40){
                                return true;
                            }
                            if (this.getX() +60 >= enemy.getX()
                                    && this.getX()+60<= enemy.getX()+60
                                    && this.getY()+40<=enemy.getY()+40
                                    && this.getY()+40>=enemy.getY()) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://左
                //让当前的坦克的坐标和Vector<EnemyTank> enemyTanks中的坦克的坐标进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector<EnemyTank> enemyTanks中取出一个坦克
                    EnemyTank enemy = enemyTanks.get(i);
                    //判断当前坦克是否是自身坦克
                    if(enemy != this){
                        //对敌人坦克方向进行判断
                        if (enemy.getDirect()==0 || enemy.getDirect()==3){//上下
                            if (this.getX()>=enemy.getX()
                                    && this.getX()<=enemy.getX()+40
                                    && this.getY() >=enemy.getY()
                                    && this.getY() <=enemy.getY()+60){
                                return true;
                            }
                            if(this.getX() >= enemy.getX()
                                    && this.getX() <= enemy.getX()+40
                                    && this.getY()+40>=enemy.getY()
                                    && this.getY()+40<=enemy.getY()+60){
                                return true;}
                        }
                        if(enemy.getDirect()==1 || enemy.getDirect()==2){//左右
                            if (this.getX()>=enemy.getX()
                                    && this.getX()<=enemy.getX()+60
                                    && this.getY()+60>=enemy.getY()
                                    && this.getY()+60<=enemy.getY()+40){
                                return true;
                            }
                            if (this.getX() >= enemy.getX()
                                    && this.getX()<= enemy.getX()+60
                                    && this.getY()+40<=enemy.getY()+40
                                    && this.getY()+40>=enemy.getY()) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://下
                //让当前的坦克的坐标和Vector<EnemyTank> enemyTanks中的坦克的坐标进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector<EnemyTank> enemyTanks中取出一个坦克
                    EnemyTank enemy = enemyTanks.get(i);
                    //判断当前坦克是否是自身坦克
                    if(enemy != this){
                        //对敌人坦克方向进行判断
                        if (enemy.getDirect()==0 || enemy.getDirect()==3){//上下
                            if (this.getX()>=enemy.getX()
                                    && this.getX()<=enemy.getX()+40
                                    && this.getY() + 60>=enemy.getY()
                                    && this.getY() +60<=enemy.getY()+60){
                                return true;
                            }
                            if(this.getX()+40 >= enemy.getX()
                                    && this.getX()+40 <= enemy.getX()+40
                                    && this.getY()+60>=enemy.getY()
                                    && this.getY()+60<=enemy.getY()+60){
                                return true;}
                        }
                        if(enemy.getDirect()==1 || enemy.getDirect()==2){//左右
                            if (this.getX()>=enemy.getX()
                                    && this.getX()<=enemy.getX()+60
                                    && this.getY()+60>=enemy.getY()
                                    && this.getY()+60<=enemy.getY()+40){
                                return true;
                            }
                            if (this.getX() +40>= enemy.getX()
                                    && this.getX()+40<= enemy.getX()+60
                                    && this.getY()+60<=enemy.getY()+40
                                    && this.getY()+60>=enemy.getY()) {
                                return true;
                            }
                        }
                    }
                }
                break;
            }
        return false;
    }

    @Override
    public void run() {//创建一个线程，用来实现敌方坦克随意移动
        while (true) {
            if(isLive && shots.size() <= 1) {//每隔一段时间进行发射炮弹
                Shot s= null;
                switch (getDirect()){
                    case 0://上
                        s = new Shot(getX() + 17, getY(), getDirect());
                        break;
                    case 1://右
                       s = new Shot(getX() + 60, getY()+17, getDirect());
                        break;
                    case 2://左
                        s = new Shot(getX(), getY()+17, getDirect());
                        break;
                    case 3:// 下
                         s = new Shot(getX()+17, getY()+60, getDirect());
                        break;
                }
                shots.add(s);//将创建的shot对象添加到集合中
                new Thread(s).start();//启动shot线程
            }
                switch (getDirect()) {//先沿当前方向移动
                    case 0://上
                        for (int i = 0; i < 30; i++) {//循环30次，移动30个单位
                            if (getY() >= 0 && !isTouchEnemyTank()) {//坦克不能移动到边界
                                moveup();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    case 1://右
                        for (int i = 0; i < 30; i++) {
                            if (getX() <= 1000 - 60&& !isTouchEnemyTank()) {//范围大小减去坦克自身大小，为坦克可以移动的距离
                                moveRight();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    case 2://左
                        for (int i = 0; i < 30; i++) {
                            if (getX() >= 0 && !isTouchEnemyTank()) {
                                moveLeft();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    case 3://下
                        for (int i = 0; i < 30; i++) {
                            if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                                moveDown();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                }

            //随机改变方向0-3
            setDirect((int)(Math.random()*4));//随机数字范围，0-3
            if(!isLive){//如果坦克死亡，结束线程
                break;
            }
        }
    }

}
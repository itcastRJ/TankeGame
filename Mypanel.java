package com.chz.TankGame3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * @Author chz
 * @Date 2024/5/29 20:20
 * @注释  坦克大战的范围
 */
@SuppressWarnings("all")
/**
 * @注释  为了使画板panel不断重绘，需要实现Runnable接口，
 重写run方法，每隔一段时间就进行重绘
 */

public class Mypanel extends JPanel  implements KeyListener,Runnable {
    Hero hero = null;
    //创建敌方坦克，放到集合
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector,用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    Vector<Hero> heroTanks = new Vector<>();
    int enemyTankSize = 3;//默认敌方坦克数量为3
    //定义一个集合，用于存储爆炸特效
    Vector<Bomb> bombs = new Vector<>();
    /**@注释 定义四张图片，用于显示爆炸效果
     *当坦克被集中之后，就会展示爆炸效果
     */
    Image image0 = null;
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public Mypanel(String key) throws FileNotFoundException {
        //判断文件是否存在，如果存在，则读取文件，将文件中的坐标和方向，设置给enemyTank，不存在就只能开始新游戏，"key = 1"
        File file = new File(Recorder.getRecordFile());
        if ( file.exists()){//判断文件是否存在
            //当游戏启动的时候，选择是否继续游戏
            nodes = Recorder.getNodesAndEnemyTankRec();
        }else {
            System.out.println("文件不存在，开始新游戏");
            key = "1";
        }

        //将MyPanel对象的enemyTanks设置给Recorder的enemyTanks
        Recorder.setEnemyTank(enemyTanks);
        hero = new Hero(500, 200);//创建(初始化)一个坦克
        hero.setSpeed(10);//设置速度
        switch (key){
            case "1":
                Recorder.resetAllEnemyTankNum();
                //创建多个敌方坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 100);//变化坐标
                    //将敌方坦克enemyTanks设置给enemyTank,放入集合
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(3);//设置初始方向,向下
                    //启动敌方坦克
                    new Thread(enemyTank).start();
                    //在敌方坦克集合中，添加炮弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //添加emyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动Shot对象
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2"://继续上一局游戏
                //创建多个敌方坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());//变化坐标
                    //将敌方坦克enemyTanks设置给enemyTank,放入集合
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirect());//设置初始方向,向下
                    //启动敌方坦克
                    new Thread(enemyTank).start();
                    //在敌方坦克集合中，添加炮弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //添加emyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动Shot对象
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误");
        }

        //创建图片对象
        image0 = Toolkit.getDefaultToolkit().getImage(Mypanel.class.getResource("/bomb_0.png"));
        image1 = Toolkit.getDefaultToolkit().getImage(Mypanel.class.getResource("/bomb_1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Mypanel.class.getResource("/bomb_2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Mypanel.class.getResource("/bomb_3.png"));

        //播放指定音频
       new PlayAudio("src/111.wav").start();

    }

    /**@编写方法，显示我方击毁坦克的数量*/
    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您击毁敌方坦克", 1000, 30);
        drawTanke(1020, 60, 0, g, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum()+" ", 1100, 100);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        showInfo(g);
        //画出自己的坦克，封装方法
        g.fillRect(0, 0, 1000, 750);//进行填充
        //提前画出爆炸效果，防止后续击中的坦克不显示爆炸效果
        g.drawImage(image0,2400,140,60,60,this);
        g.drawImage(image1,2400,240,60,60,this);
        g.drawImage(image2,2400,340,60,60,this);
        g.drawImage(image3,2400,340,60,60,this);
        if (hero != null && hero.isLive) {
            drawTanke(hero.getX(), hero.getY(), hero.getDirect(), g, 1);
        }
        /**
         @注释 在画板上画出炮弹
         */
//        if (hero.shot != null && hero.shot.isLive == true) {//对炮弹状态进行一个判断
//            g.draw3DRect(hero.shot.x, hero.shot.y, 5, 5, false);
//       }
        /**对炮弹进行循环遍历，进行重绘*/
        for (int i = 0; i < Hero.shots.size(); i++) {
            Shot shot = Hero.shots.get(i);
            if (shot != null && shot.isLive == true) {//对炮弹状态进行一个判断
                g.draw3DRect(shot.x, shot.y, 5, 5, false);
            } else {
                Hero.shots.remove(shot);
            }
        }
        //画出敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //获取坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTanke(enemyTank.getX(), enemyTank.getY(), enemyTank.getDirect(), g, 0);
                if (enemyTank.shots != null) {
                    for (int j = 0; j < enemyTank.shots.size(); j++) {
                        //取出子弹
                        Shot shot = enemyTank.shots.get(j);
                        //绘制子弹
                        if (shot.isLive) {
                            g.draw3DRect(shot.x, shot.y, 5, 5, false);
                        } else {
                            //从集合中移除子弹
                            enemyTank.shots.remove(shot);
                        }
                    }
                }
            }
        }
        /** @注释 爆炸效果随着生命周期的衰减而变化 */
        Iterator<Bomb> bombIterator = bombs.iterator();
        while (bombIterator.hasNext()) {
            Bomb bomb = bombIterator.next();
            if (bomb.life > 6) {
                g.drawImage(image0, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 0) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            if (bomb.life <= 0) {
                bombIterator.remove();
            }
        }
    }
    //绘画坦克的封装方法

    /**
     * @param x      坦克的左上角 x 坐标
     * @param y      坦克的左上角 y 坐标
     * @param direct 坦克方向 ( 上下左右 )
     * @param g      画笔
     * @param type   坦克类型
     */
    public void drawTanke(int x, int y, int direct, Graphics g, int type) {

        //坦克类型
        switch (type) {
            case 0://玩家坦克
                g.setColor(Color.cyan);
                Hero hero = new Hero(x, y);
                hero.moveDown();
                break;
            case 1://敌人坦克
                g.setColor(Color.yellow);
                break;
        }

        /**
         * @param 绘制坦克
         * @param raised 表示图形是否凸起或者凹陷，flase表示凹陷，true表示凸起
         */
        //坦克方向
        switch (direct) {
            case 0://表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                /* g.fill3DRect(x+17,y-10,5,20,false);//画出炮管,3D样式*/
                g.drawLine(x + 20, y + 30, x + 20, y);//直线炮管
                break;
            case 1://表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                /* g.fill3DRect(x+17,y-10,5,20,false);//画出炮管,3D样式*/
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//直线炮管
                break;
            case 2://表示向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                /* g.fill3DRect(x+17,y-10,5,20,false);//画出炮管,3D样式*/
                g.drawLine(x + 30, y + 20, x, y + 20);//直线炮管
                break;
            case 3://表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                /* g.fill3DRect(x+17,y-10,5,20,false);//画出炮管,3D样式*/
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//直线炮管
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    /**
     * @注释 hitEnemyTank() 判断我方的坦克是否击中了敌方坦克
     */
    public void hitEnemyTank(Shot s, EnemyTank enemyTank) {//单颗炮弹击中敌方坦克
        boolean isHit = false;//判断炮弹是否击中了坦克
        switch (enemyTank.getDirect()) {
            case 0://表示向上
            case 3://表示向下
                //判断炮弹坐标是否出现在了坦克坐标里面
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    isHit = true;
                }
                break;
            case 1://表示向右
            case 2://表示向左
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    isHit = true;
                }
                break;
        }
        if (isHit) {
            /**@注释 创建爆炸对象，加入到bombs集合*/
            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
            bombs.add(bomb);
            // 更新击毁数量
            Recorder.addAllEnemyTankNum();
            // 将enemyTank从集合中移除
            enemyTanks.remove(enemyTank);
            System.out.println("炮弹坐标为：" + s.x + "," + s.y);
            System.out.println("击中敌方坦克，位置：(" + enemyTank.getX() + ", " + enemyTank.getY() + ")");
            System.out.println("添加爆炸效果，位置：(" + bomb.x + ", " + bomb.y + ")");
        } else {
            System.out.println("炮弹未击中任何敌方坦克");
        }
    }


    /**
     * @注释 hitHero() 判断敌方坦克是否击中了我方的坦克
     */
    public void hitHero(Shot s, Hero hero) { // 我方坦克被击中
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i); // 遍历敌方坦克
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j); // 取出子弹进行碰撞检测
                if (hero.isLive && shot.isLive) { // 如果我方坦克活着，并且敌人坦克子弹活着，则进行碰撞检测
                    switch (hero.getDirect()) {
                        case 0: // 表示向上
                        case 3: // 表示向下
                            if (shot.x > hero.getX() && shot.x < hero.getX() + 40 && shot.y > hero.getY() && shot.y < hero.getY() + 60) {
                                shot.isLive = false;
                                hero.isLive = false;
                                // 添加爆炸效果
                                Bomb bomb = new Bomb(hero.getX(), hero.getY());
                                bombs.add(bomb);
                                // 移除坦克
                                heroTanks.remove(hero);
                                // 停止坦克发射的所有子弹
                                hero.shots.clear();
                                System.out.println("我方坦克被击中，位置：(" + hero.getX() + ", " + hero.getY() + ")");
                            }
                            break;
                        case 1: // 表示向右
                        case 2: // 表示向左
                            if (shot.x > hero.getX() && shot.x < hero.getX() + 60 && shot.y > hero.getY() && shot.y < hero.getY() + 40) {
                                shot.isLive = false;
                                hero.isLive = false;
                                // 添加爆炸效果
                                Bomb bomb = new Bomb(hero.getX(), hero.getY());
                                bombs.add(bomb);
                                // 移除坦克
                                heroTanks.remove(hero);
                                // 停止坦克发射的所有子弹
                                hero.shots.clear();
                                System.out.println("我方坦克被击中，位置：(" + hero.getX() + ", " + hero.getY() + ")");
                            }
                            break;
                    }
                }
            }
        }
        reformat(); // 重绘方法
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//当按下按键的时候，就会触发
        //System.out.println((char)e.getKeyCode()+"被按下");
        //根据用户按键，来处理小球的移动
        if (e.getKeyCode()==KeyEvent.VK_DOWN){//对应向下的键
          hero.setDirect(3);
          if (hero.getY()<=750-105){
          hero.moveDown();}
        }else if (e.getKeyCode()==KeyEvent.VK_UP){//对应向上的键
         hero.setDirect(0);
         if (hero.getY()>=5){
         hero.moveup();}
        }else if (e.getKeyCode()==KeyEvent.VK_RIGHT){//对应向右的键
         hero.setDirect(1);
         if (hero.getX()<=1000-85){
         hero.moveRight();}
        }else if (e.getKeyCode()==KeyEvent.VK_LEFT){//对应向左的键
        hero.setDirect(2);
        if (hero.getX()>=5){
        hero.moveLeft();}
        }
        //如果用户按下J 键，就发射一个炮弹
        if (e.getKeyCode()==KeyEvent.VK_J){
                hero.shotEnemyTank();
                }
        reformat();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void reformat(){//将画板重绘进行一个封装
        this.repaint();
    }/**@注释 每隔100毫秒，刷新画板,更新坦克和炮弹状态*/
    @Override
    public void run() {
        while (true) {//判断每一颗炮弹是否击中了敌方坦克
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < hero.shots.size(); i++) {
                Shot shot = hero.shots.get(i);//获取到当前遍历到的炮弹
                if(shot!=null&& shot.isLive){//判断炮弹状态
                    for (int j= 0;j < enemyTanks.size() ; j++) {//遍历敌方坦克，判断是否击中了敌方坦克
                        EnemyTank enemyTank = enemyTanks.get(j);
                        hitEnemyTank(hero.shot,enemyTank);
                    }
                }else {
                    hero.shots.remove(shot);
                }
            }

            // 检查每个敌方坦克的子弹是否击中了玩家坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    for (int j = 0; j < enemyTank.shots.size(); j++) {
                        Shot shot = enemyTank.shots.get(j);
                        if (shot != null && shot.isLive) {
                            hitHero(shot, hero);
                        } else {
                            enemyTank.shots.remove(shot);
                        }
                    }
                }
            }
            reformat();//刷新画板
        }
    }

    /**
     * @注释 如果敌方坦克的数量小于3，就生成一辆敌方坦克
     */
   /* public void createEnemyTank(){
        if (enemyTanks.size()<3 *//*&& enemyTanks.size()==0*//*) {
            new Thread(() -> {
                try {
                    //生成坦克
                    EnemyTank enemyTank1 = new EnemyTank(100, 100);
                    enemyTank1.setDirect((int)(Math.random()*4));//随机数字范围，0-3
                    //添加到集合中
                    enemyTank1.setSpeed((int)(Math.random()*5+1));
                    //启动新坦克线程
                    new Thread(enemyTank1).start();
                    synchronized (enemyTanks) {
                        enemyTanks.add(enemyTank1);
                    }
                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }*/

/**
 * @注释 判断双方坦克是否重叠，如果重叠，敌方坦克改变运行方向，玩家坦克改变方向
  */
    public void overlap(EnemyTank enemyTank, Hero hero){

    }
}

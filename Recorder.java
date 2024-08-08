package com.chz.TankGame3;

import java.io.*;
import java.util.Vector;

/**
 * @Author chz
 * @Date 2024/8/3 16:42
 * @说明 ：我方击毁敌人坦克数
 */
@SuppressWarnings("all")
public class Recorder {
    //定义变量，记录击毁坦克数
    private static int allEnemyTankNum=0;
    //定义IO流对象，用于准备写数据到文件中
    private static BufferedWriter bw=null;
    private static BufferedReader br=null;
    private static String recordFile="src\\myRecord.txt";

    public static String getRecordFile() {
        return recordFile;
    }

    private static Vector<EnemyTank> enemyTank = null;

    //定义一个Node的Vector,用于保存敌人的信息，继续游戏时，读取Node的Vector
    private static Vector<Node> nodes = new Vector<>();


    public static void setEnemyTank(Vector<EnemyTank> enemyTank) {
        Recorder.enemyTank = enemyTank;
    }
    //定义get，set方法，获取和修改击毁坦克数量

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当我方坦克击毁敌人坦克时，增加击毁坦克数量AllEnemyTankNum
    public static void addAllEnemyTankNum()
    {
       Recorder.allEnemyTankNum++;
    }

    // 开始新游戏的时候，重置击毁坦克数量
    public static void resetAllEnemyTankNum() {
        Recorder.allEnemyTankNum = 0;
    }

    /**@说明  当游戏结束退出时，将击毁坦克数量写入到文件中
     * 对keepRecord方法进行升级，保存敌人坦克的坐标和方向
     * */
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\r\n");
            /**2获取敌人坦克的Vector里面的各种信息，用来方便继续游戏*/
            //遍历敌人坦克的Vector,获取到坦克的坐标和方向
            //如何获取，可定义一个属性，通过set方法得到敌人坦克的Vector
            for (int i = 0; i <enemyTank.size() ; i++) {
                EnemyTank enemyTank1 = enemyTank.get(i);//获取到坦克
                if (enemyTank1.isLive){//如果坦克还活着
                    String record=enemyTank1.getX()+" "+enemyTank1.getY()+" "+enemyTank1.getDirect();//获取坦克的坐标和方向
                    bw.write(record+"\r\n");//写入到文件中
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (bw!=null){
                bw.close();}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**@说明  从文件中读取数据，恢复敌人坦克的信息,继续上一局游戏*/
    public static Vector<Node> getNodesAndEnemyTankRec() throws FileNotFoundException {
        br = new BufferedReader(new FileReader(recordFile));
        try {
            allEnemyTankNum = Integer.parseInt(br.readLine());//读取击毁坦克数量
            //循环读取文件，生成nodes集合
            String line="";
            while ((line=br.readLine())!=null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (br!=null){
                    br.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return nodes;
    }



}

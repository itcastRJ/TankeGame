package com.chz.TankGame3;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TankGame3 extends  JFrame{
    Mypanel mp=null;//创建一个面板
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws FileNotFoundException {


        TankGame3 tankGame3 = new TankGame3();
    }
    public TankGame3() throws FileNotFoundException {//构造器
        System.out.println("请输入你的选择 1：开始新游戏 2：继续上一局游戏");
        String key = scanner.next();
        mp = new Mypanel(key);//初始化游戏面板区域,对应不同选择
        //将mp添加到线程之中吗，并且启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板加入到窗口
        this.setSize(1200,750);//设置窗口大小
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭方式
        this.setVisible(true);//设置可见
        //当窗口关闭时，将上一关击败坦克数写入文件
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.out.println("游戏结束");
                System.exit(0);
            }
        });
    }
}

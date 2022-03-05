//this is the core or the base class on which the whole program is working
package com.pranav;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import java.awt.event.*;

import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=80;
    final int[] x= new int[GAME_UNITS];// holding the x coordinates of the body parts of snake
    final int[] y= new int[GAME_UNITS];//holding the y coordinates of the body parts of snake
    int bodyParts=6;
    int applesEaten;
    int appleX;
    int appleY;
    int score =0;
    char direction='R'; // right left up down
    boolean running= false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();




    }
    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw (Graphics g){
        if(running){
            //if you want to add grid lines on the screen add this code also
          for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
          }
          g.setColor(Color.yellow);
          g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
          for(int i=0;i<bodyParts;i++) {
             if (i == 0) {//you are dealing with the head of snake ,so give it a different color.
                g.setColor(Color.green);
                g.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
             }
             else {
                g.setColor(Color.red);
                g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);

             }

          }g.setColor(Color.green);
            g.setFont(new Font("Ink Free",Font.PLAIN,35));
            FontMetrics metrics= getFontMetrics(g.getFont());
            g.drawString("SCORE:"+score,(SCREEN_WIDTH - metrics.stringWidth("SCORE"+score))/2,g.getFont().getSize());}
        else{gameOver(g);}
    }
    public void newApple(){
        appleX= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY= random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
        for(int i=bodyParts;i>0;i--){
          x[i]=x[i-1] ;
          y[i]=y[i-1];
        }
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkApple(){
        if(x[0]==appleX && y[0]==appleY){
            bodyParts++;
            applesEaten++;
            score++;
            newApple();
        }

    }
    public void checkCollisions(){
        for(int i=bodyParts;i>0;i--){
            if(x[0]==x[i]&& y[0]==y[i]){
                running=false;
            }
        }
        //check if the head touches any of the border
        //left border
        if(x[0]<0){
            running=false;
        }
        //right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //top border
        if(y[0]<0){
            running=false;
        }
        //bottom border
        if(y[0]==SCREEN_HEIGHT){
            running=false;
        }
        if(!running){
            timer.stop();
        }

    }
    public void gameOver(Graphics g){
        // this would display score:
        g.setColor(Color.green);
        g.setFont(new Font("Ink Free",Font.BOLD,50));
        FontMetrics metrics1= getFontMetrics(g.getFont());
        g.drawString("SCORE:"+score,(SCREEN_WIDTH - metrics1.stringWidth("SCORE"+score))/2,g.getFont().getSize());
        //set game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2= getFontMetrics(g.getFont());
        g.drawString("GAME OVER!",(SCREEN_WIDTH - metrics2.stringWidth("GAME OVER!"))/2,SCREEN_HEIGHT/2);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //TO DO - auto generated method stub
        if(running){
            move();
            checkApple();
            checkCollisions();

        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(!(direction=='R')){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(!(direction=='L')){
                        direction='R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if(!(direction=='D')){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(!(direction=='U')){
                        direction='D';
                    }
                    break;


            }

        }
    }
}

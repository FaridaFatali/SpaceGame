package com.mycompany.spacegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Farida Fatali
 */

class Fire{
    private int x;
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}


public class Game extends JPanel implements KeyListener, ActionListener{
    Timer timer = new Timer(5, this);

    private int spentTime = 0;
    private int usedFire = 0;
    private BufferedImage image;
    private ArrayList<Fire> fires = new ArrayList<>();
    private int firedirY = 1;
    private int ballX = 0;
    private int balldirX = 2;
    private int spaceShipX = 0;
    private int dirSpaceX = 20;
    
    public boolean control(){
        for(Fire fire : fires){
            if(new Rectangle(fire.getX(), fire.getY(), 7, 15).intersects(new Rectangle(ballX, 0, 7, 15))){
                return true;
            }
        }
        return false;
    }

    public Game() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("rocket.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        spentTime += 3;
        g.setColor(Color.red);
        g.fillOval(ballX, 0, 20, 20);
        g.drawImage(image, spaceShipX, 490, image.getWidth() / 7, image.getHeight() / 7, this);

        for(Fire fire : fires){
            if(fire.getY() < 0){
                fires.remove(fire);
            }
        }
        
        g.setColor(Color.WHITE);
        
        for(Fire fire : fires){
            g.fillRect(fire.getX(), fire.getY(), 7, 15);
        }
        
        if(control()){
            timer.stop();
            String message = "You won!" +
                    "\nUsed fire : " + usedFire +
                    "\nSpent time : " + spentTime / 1000.0 + "  seconds";
            
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        switch (c) {
            case KeyEvent.VK_LEFT -> {
                if(spaceShipX <= 0){
                    spaceShipX = 0;
                } else {
                    spaceShipX -= dirSpaceX;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if(spaceShipX >= 720){
                    spaceShipX = 720;
                } else {
                    spaceShipX += dirSpaceX;
                }
            }
            case KeyEvent.VK_CONTROL -> {
                fires.add(new Fire(spaceShipX + 32 , 470));
                usedFire++;
            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Fire fire : fires){
            fire.setY(fire.getY() - firedirY);
        }
        
        ballX += balldirX;
        if(ballX >= 750){
            balldirX = -balldirX;
        }
        if (ballX <= 0) {
            balldirX = -balldirX;
        }
        
        repaint();
    }
}

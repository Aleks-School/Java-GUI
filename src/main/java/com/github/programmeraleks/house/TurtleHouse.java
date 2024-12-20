package com.github.programmeraleks.house;

import com.github.programmeraleks.house.Resources.Resources;
import com.github.programmeraleks.house.Resources.Vector2;
import com.github.programmeraleks.house.Turtle.AnimAnimal;
import com.github.programmeraleks.house.Turtle.Pen;
import com.github.programmeraleks.house.Turtle.Turtle;
import com.github.programmeraleks.house.Turtle.World;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class TurtleHouse
{
    private static final World WORLD = new World(600,600);
    private static final AnimAnimal[] ANIMALS = new AnimAnimal[4];

    static { // Initialize and add animals to the array
        Turtle turtle = new Turtle(WORLD);
        turtle.setBodyColor(new Color(255,240,0));
        turtle.setShellColor(new Color(255,0,0));
        turtle.adjust(10,10);
        turtle.hide();

        AnimAnimal puffle = new AnimAnimal(WORLD, "Puffle.png");
        Vector2 puffleSize = puffle.getSize();
        puffle.addState(puffleSize.add(new Vector2(-0.1*puffleSize.x,0.15*puffleSize.y)));
        puffle.addState(puffleSize.add(new Vector2(0.15*puffleSize.x,-0.1*puffleSize.y)));
        puffle.hide();

        AnimAnimal bird = new AnimAnimal(WORLD, "Bird.png");
        bird.setSpeed(275);
        bird.adjust(50,100,90);
        bird.penUp();
        bird.show();

        AnimAnimal blueBird = new AnimAnimal(WORLD, "BlueBird.png");
        blueBird.setSpeed(200);
        blueBird.adjust(570,200,270);
        blueBird.penUp();
        blueBird.show();

        ANIMALS[0] = puffle;
        ANIMALS[1] = bird;
        ANIMALS[2] = blueBird;
        //ANIMALS[3] = turtle;
    }

    public static void main(String[] args) throws Exception {

        new Thread(() -> { // Starts running an animation loop for the birds
            short state = 0;

            while (true) {
                if (state == 0) {
                    ANIMALS[1].turnToFace(30,150);
                    ANIMALS[1].moveTo(30,150);
                    state++;
                } else if (state == 1) {
                    ANIMALS[1].turnToFace(570,200);
                    ANIMALS[1].moveTo(570,200);
                    state %= 1;
                }
            }
        }).start();

        Clip ambience = Resources.getAudio("BirdSound.au");
        Resources.adjustVolume(ambience, -0.5f);
        
        WORLD.setVisible(true);

        Vector2 startPos = new Vector2(300, 300);

        Pen pen = ANIMALS[0].getPen();

        new Thread(() -> {
            short state = 0;
            while (true) {
                if (state == 0) {
                    ANIMALS[2].setHeading(90);
                    ANIMALS[2].moveTo(570,125);
                    state++;
                } else if (state == 1) {
                    ANIMALS[2].turnToFace(50,100);
                    ANIMALS[2].moveTo(50,100);
                    state %= 1;
                }
            }
        }).start();

        Vector2[] leftWindow = {
            startPos.add(new Vector2(-40, 90)),
            startPos.add(new Vector2(-25, 90)),
            startPos.add(new Vector2(-25, 60)),
            startPos.add(new Vector2(-40, 60))
        };
        Vector2[] rightWindow = {
            startPos.add(new Vector2(40, 90)),
            startPos.add(new Vector2(25, 90)),
            startPos.add(new Vector2(25, 60)),
            startPos.add(new Vector2(40, 60))
        };

        /*
        *  Adds a second layer and draws some on it so that more details
        *  can be added with a certain amount of depth displayed in the world.
        */
        JComponent secondLayer = new JComponent() {
            int[] cloudX = {525,525,500,500,420,420,350,350,400,400,500,500};
            int[] cloudY = {100,130,130,140,140,125,125,100,100,70,70,100};

            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255,255,255));
                g.fillPolygon(cloudX, cloudY, cloudX.length);

                int points = 4;
                int[] leftWinX = new int[points];
                int[] leftWinY = new int[points];
                int[] rightWinX = new int[points];
                int[] rightWinY = new int[points];
                for (int i=0;i<points;i++) {
                    leftWinX[i] = (int)leftWindow[i].x;
                    leftWinY[i] = (int)leftWindow[i].y;
                    rightWinX[i] = (int)rightWindow[i].x;
                    rightWinY[i] = (int)rightWindow[i].y;
                }
                g.setColor(new Color(153,255,255));
                g.fillPolygon(leftWinX, leftWinY, points);
                g.fillPolygon(rightWinX, rightWinY, points);
            }
        };

        JComponent titleLayer = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(46, 45, 45));
                g.fillRect(0, 0, 600, 600);
            }
        };

        JButton startButton = new JButton("Start");
        startButton.setSize(200, 75);
        startButton.addActionListener(actionEvent -> {
            startButton.setVisible(false);
            titleLayer.setVisible(false);
            WORLD.remove(titleLayer);
        });

        titleLayer.add(startButton);
        titleLayer.setSize(WORLD.getWidth(), WORLD.getHeight());
        titleLayer.setVisible(true);

        secondLayer.setSize(WORLD.getWidth(), WORLD.getHeight());
        secondLayer.setVisible(true);

        WORLD.add(secondLayer);
        //world.add(titleLayer);

        // Draws the scenery and house
        for (float scale=1; scale < 2.5; scale+=0.1) {
            startPos = startPos.add(new Vector2(-5,1));

            Vector2[] walls = {
                startPos.add(new Vector2(50, 50).mul(scale)),
                startPos.add(new Vector2(50, 100).mul(scale)),
                startPos.add(new Vector2(15, 100).mul(scale)),
                startPos.add(new Vector2(15, 75).mul(scale)),
                startPos.add(new Vector2(-15, 75).mul(scale)),
                startPos.add(new Vector2(-15, 100).mul(scale)),
                startPos.add(new Vector2(-50, 100).mul(scale)),
                startPos.add(new Vector2(-50, 50).mul(scale)),
            };

            leftWindow[0] = startPos.add(new Vector2(-40, 90).mul(scale));
            leftWindow[1] = startPos.add(new Vector2(-25, 90).mul(scale));
            leftWindow[2] = startPos.add(new Vector2(-25, 60).mul(scale));
            leftWindow[3] = startPos.add(new Vector2(-40, 60).mul(scale));
            
            rightWindow[0] = startPos.add(new Vector2(40, 90).mul(scale));
            rightWindow[1] = startPos.add(new Vector2(25, 90).mul(scale));
            rightWindow[2] = startPos.add(new Vector2(25, 60).mul(scale));
            rightWindow[3] = startPos.add(new Vector2(40, 60).mul(scale));
            
            Vector2 brc = walls[walls.length-1].sub(Vector2.xAxis.mul(10*scale));
            Vector2[] mansardRoof = {
                brc,
                brc.add(new Vector2(10,-43).mul(scale)),
                brc.add(new Vector2(110,-43).mul(scale)),
                walls[0].add(Vector2.xAxis.mul(10*scale))
            };
    
            Vector2 chimneyStart = mansardRoof[2].sub(new Vector2(20,0).mul(scale));
            Vector2[] chimneyPoints = {
                chimneyStart,
                chimneyStart.sub(new Vector2(0,16).mul(scale)),
                chimneyStart.sub(new Vector2(-4,16).mul(scale)),
                chimneyStart.sub(new Vector2(4,20).mul(scale)),
                chimneyStart.sub(new Vector2(12,16).mul(scale)),
                chimneyStart.sub(new Vector2(8,16).mul(scale)),
                chimneyStart.sub(new Vector2(8,0).mul(scale))
            };
    
            Vector2[] doorPoints = {
                startPos.add(new Vector2(-15, 75).mul(scale)),
                startPos.add(new Vector2(15, 75).mul(scale)),
                startPos.add(new Vector2(15, 100).mul(scale)),
                startPos.add(new Vector2(-15, 100).mul(scale))
            };

            Map<Vector2[], Map<Boolean,Color>> house = new HashMap<Vector2[],Map<Boolean,Color>>();
            {Map<Boolean,Color> settings = new HashMap<Boolean,Color>(); settings.put(true,new Color(225,61,21)); house.put(walls, settings);}
            {Map<Boolean,Color> settings = new HashMap<Boolean,Color>(); settings.put(true,new Color(120,50,70)); house.put(doorPoints, settings);}
            {Map<Boolean,Color> settings = new HashMap<Boolean,Color>(); settings.put(true,new Color(60,60,60)); house.put(mansardRoof, settings);}
            {Map<Boolean,Color> settings = new HashMap<Boolean,Color>(); settings.put(true,new Color(30,30,30)); house.put(chimneyPoints, settings);}
            //{Map<Boolean,Color> settings = new HashMap<Boolean,Color>(); settings.put(true,new Color(153,255,255)); house.put(leftWindow, settings);}
            //{Map<Boolean,Color> settings = new HashMap<Boolean,Color>(); settings.put(true,new Color(153,255,255)); house.put(rightWindow, settings);}
            
            //Vector2 totalSize = new Vector2(mansardRoof[2].x - mansardRoof[0].x, walls[1].y - chimneyPoints[3].y).absolute(); // the box size the house components form together
            
            pen.clear();
            for (Map.Entry<Vector2[], Map<Boolean,Color>> entry : house.entrySet()) {
                for (Map.Entry<Boolean,Color> settings : entry.getValue().entrySet()) {
                    pen.setColor(settings.getValue());
                    pen.drawPolygon(entry.getKey(), settings.getKey());
                }
            }

            Thread.sleep((long)50);
        }

        Vector2 pufflePos = startPos.add(new Vector2(0, 100).mul(2.5));

        ANIMALS[0].adjust(pufflePos);
        ANIMALS[0].show();
        ANIMALS[0].penUp();

        // Runs a loop in order to animate the scene
        while (true) { //Jump animation
            if (ANIMALS[0].getNumStates() > 0) {ANIMALS[0].setState(1);}
            ANIMALS[0].setSpeed(150);
            ANIMALS[0].moveTo(pufflePos.sub(Vector2.yAxis.mul(ANIMALS[0].getHeight()/2d)));
            if (ANIMALS[0].getNumStates() > 0) {ANIMALS[0].setState(0);}
            ANIMALS[0].setSpeed(250);
            ANIMALS[0].moveTo(pufflePos);
            if (ANIMALS[0].getNumStates() > 0) {ANIMALS[0].setState(2);}
            ANIMALS[0].waitMil(100);
            if (ANIMALS[0].getNumStates() > 0) {ANIMALS[0].setState(0);}
            ANIMALS[0].waitMil(1000);
        }
    }
}
package com.github.programmeraleks.house.Resources;

import com.github.programmeraleks.house.TurtleHouse;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Random;

public class Resources {
    // A basic function for getting a random number
    public static int random(long seed, int min, int max) {
        return min + new Random(seed).nextInt(max - min + 1);
    }

    // Gets a file from the resources folder
    public static File getFile(String filePath) throws IOException {
        File sourceFile = new File(TurtleHouse.class.getResource(filePath).getFile());
        return sourceFile;
    }

    // Gets a BufferedImage from the resources folder
    public static BufferedImage getImage(String filePath) throws IOException {
        InputStream imageUrl = TurtleHouse.class.getResourceAsStream(filePath);
        return ImageIO.read(imageUrl);
    }

    // Gets a BufferedImage from the resources folder
    public static BufferedImage getImage(File imageFile) throws IOException {
        return ImageIO.read(imageFile);
    }

    public static Vector2 getImageSize(Image image) {return new Vector2(image.getWidth(null),image.getHeight(null));}

    // Gets an audio file
    public static Clip getAudio(String filePath) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        InputStream inputStream = new BufferedInputStream(TurtleHouse.class.getResourceAsStream(filePath));
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

        Clip audioClip = AudioSystem.getClip();
        audioClip.open(audioStream);

        return audioClip;
    }

    // Calculates and returns the current volume
    public static float getVolume(Clip audio) {
        FloatControl control = (FloatControl)audio.getControl(FloatControl.Type.MASTER_GAIN);
        return (float)Utility.invLerp(control.getMinimum(), control.getMaximum(), control.getValue());
    }

    // Sets the volume of an audio clip, with 0 being silent and 1 being the maximum volume
    public static void setVolume(Clip audio, float volume) {
        FloatControl control = (FloatControl)audio.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue((float)Utility.lerp(control.getMinimum(), control.getMaximum(), volume));
    }

    /*
     * Adjusts the volume of an audio clip relative to the current
     * volume by the alpha value, with -1 being silence from the
     * current volume, 0 being no change, and 1 being the maximum
     * volume from the current volume.
     */
    public static void adjustVolume(Clip audio, float alpha) {
        FloatControl control = (FloatControl)audio.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue((float)Utility.lerp(control.getValue(), ((alpha < 0) ? control.getMinimum() : control.getMaximum()), Math.abs(alpha)));
    }

    public static BufferedImage flip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);  
        g.dispose();  
        return dimg;  
    }
    public static BufferedImage flip(BufferedImage img, boolean vertical) {
        if (vertical) {
            int w = img.getWidth();  
            int h = img.getHeight();  
            BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());  
            Graphics2D g = dimg.createGraphics();  
            g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);  
            g.dispose();  
            return dimg;
        } else {return flip(img);}
    }

    public static BufferedImage toBufferedImage(Image image) throws InterruptedException {
        MediaTracker mt = new MediaTracker(new JPanel());
        mt.addImage(image, 0);
        mt.waitForAll();

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }

    public static void setImageColor(ImageView image, Color color) {
        Effect effect = image.getEffect();

        ColorAdjust colorAdjust = ((effect != null) && (effect instanceof ColorAdjust)) ? (ColorAdjust)effect : new ColorAdjust();
        colorAdjust.setHue(Utility.map(0, 360, -1, 1, (color.getHue()+180)%360));
        colorAdjust.setBrightness(Utility.map(0, 1, -1, 0, color.getBrightness()));
        colorAdjust.setSaturation(color.getSaturation());

        image.setEffect(colorAdjust);
    }

    // Returns a random color
    public static java.awt.Color getRandomColor() {
        Random seedGenerator = new Random(System.currentTimeMillis());
        int total = 300;

        int r = random(seedGenerator.nextLong(), 0, 255);
        total -= r;
        int g = random(new Random(seedGenerator.nextLong()).nextLong(), 0, Math.min(255,total));

        return new java.awt.Color(r, g, Math.min(255,total-g));
    }

    // Inverts the color hue while retain the color saturation and brightness
    public static Color invertHue(Color color) {
        double hue = (color.getHue() + 180) % 360;
        double saturation = color.getSaturation();
        double brightness = Utility.map(0, 1, 0, 0.5, color.getBrightness());

        System.out.printf("(%f, %f, %f)\n", hue, saturation, brightness);
        return Color.hsb(hue, saturation, brightness);
    }
}
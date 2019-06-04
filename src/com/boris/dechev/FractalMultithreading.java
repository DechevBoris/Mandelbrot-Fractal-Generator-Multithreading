package com.boris.dechev;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FractalMultithreading {

    private int width;
    private int height;
    private int numberOfThreads;
    private String fileName;
    private boolean isQuiet;


    public FractalMultithreading(int width, int height, int numberOfThreads, String fileName, boolean isQuiet) {
        this.width = width;
        this.height = height;
        this.numberOfThreads = numberOfThreads;
        this.fileName = fileName;
        this.isQuiet = isQuiet;
    }


    public void run(){
        Timer timer = new Timer();
        timer.start();

        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        int maxIter = 700;

        int[] colors = new int[maxIter];
        for (int i = 0; i < maxIter; i++) {
            colors[i] = Color.HSBtoRGB(i / 256.0f, 1, i / (i + 8.0f));
        }

        int portion = this.height / this.numberOfThreads;
        int leftover = this.height % portion;

        Thread[] threads = new Thread[this.numberOfThreads];

        for (int i = 0; i < this.numberOfThreads; i++) {
            int start = i * portion;
            int end = (i + 1) * portion;

            if(i == this.numberOfThreads - 1){
                start += leftover;
            }

            MandelbrotGenerator fractal = new MandelbrotGenerator(image, colors, start, end, i + 1, this.isQuiet);
            threads[i] = new Thread(fractal);
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            }
            catch (Exception e){
                System.out.println("Error while joining threads: " + e);
            }
        }

        try {
            ImageIO.write(image, "png", new File(this.fileName));
        }
        catch (Exception e){
            System.out.println("Error while writing to file: " + e);
        }

        if(!this.isQuiet){
            System.out.println("Threads used in current run: <" + this.numberOfThreads + ">.");
        }
        System.out.println("Total execution time for current run: " + timer.stop() + " millis.");
    }
}

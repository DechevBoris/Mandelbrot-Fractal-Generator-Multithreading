package com.boris.dechev;
import org.apache.commons.math3.complex.Complex;

import java.awt.image.BufferedImage;

public class MandelbrotGenerator implements Runnable {

    private BufferedImage image;
    private int[] colors;
    private int start;
    private int end;
    private int threadNumber;
    private int maxIter;
    private boolean isQuiet;


    public MandelbrotGenerator(BufferedImage image, int[] colors, int start, int end, int threadNumber, boolean isQuiet) {
        this.image = image;
        this.colors = colors;
        this.start = start;
        this.end = end;
        this.threadNumber = threadNumber;
        this.maxIter = colors.length;
        this.isQuiet = isQuiet;
    }


    @Override
    public void run() {
        Timer timer = new Timer();
        timer.start();

        if(!this.isQuiet){
            System.out.println("Thread <" + this.threadNumber + "> started.");
        }

        int width = this.image.getWidth();
        int height = this.image.getHeight();

        for (int col = 0; col < width; col++) {
            double c_re = (col - width / 2) * 4.0 / width;
            for (int row = this.start; row < this.end; row++) {
                double c_im = (row - height / 2) * 4.0 / width;

                Complex c = new Complex(c_re, c_im);
                Complex z = new Complex(0, 0);
                int iterations = 0;

                while(iterations < this.maxIter && z.abs() <= 2.0){
                    z = c.multiply(z.multiply(-1).exp()).add(z.multiply(z));
                    iterations++;
                }

                if(iterations < this.maxIter){
                    this.image.setRGB(col,row, this.colors[iterations]);
                }
                else {
                    this.image.setRGB(col, row, this.colors[0]);
                }
            }
        }

        if(!this.isQuiet){
            System.out.println("Thread <" + this.threadNumber + "> stopped. Thread <" + this.threadNumber + "> " +
                    "execution time was " + timer.stop() + " millis.");
        }
    }
}

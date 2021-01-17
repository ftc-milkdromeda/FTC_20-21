package Milkdromeda.Image;

import io.nayuki.bmpio.AbstractRgb888Image;
import io.nayuki.bmpio.BmpImage;
import io.nayuki.bmpio.BmpWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Image {
    public Image(int width, int height, boolean grayscale) {
        this.width = width;
        this.height = height;
        this.grayscale = grayscale;
    }

    public boolean setPixels(double[] pixels) {
        if(grayscale && pixels.length != this.width * this.height)
            return false;
        if(!grayscale && pixels.length != this.width * this.height * 3)
            return false;

        this.pixels = new double[pixels.length];
        for(int a = 0; a < pixels.length; a++)
            this.pixels[a] = pixels[a];

        return true;
    }
    public boolean setPixels(int[] pixels) {
        if(grayscale && pixels.length != this.width * this.height)
            return false;
        if(!grayscale && pixels.length != this.width * this.height * 3)
            return false;

        this.pixels = new double[pixels.length];
        for(int a = 0; a < pixels.length; a++)
            this.pixels[a] = pixels[a] / 256f;

        return true;
    }

    public double[] getPixels() {
        return this.pixels;
    }
    public boolean isGrayscale() {
        return this.grayscale;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }

    public void makeGrayscale() {
        if(this.grayscale)
            return;

        double pixels[] = new double[this.width * this.height];
        for(int a = 0; a < pixels.length; a++)
            pixels[a] = (this.pixels[a * 3] + this.pixels[a * 3 + 1] + this.pixels[a * 3 * 2]) / 3;

        this.grayscale = true;
        this.pixels = pixels;
    }
    public boolean crop(int x1, int y1, int x2, int y2) {
       if(x1 > x2 || y1 > y2)
           return false;
       if(x2 >= this.width || y2 >= this.height)
           return false;

       double pixels[] = new double[(grayscale ? 1 : 3) *(x2 - x1) * (y2 - y1)];

       for(int x = x1, counter = 0; x < x2; x++) {
           for(int y = y1; y < y2; y++, counter++) {
               pixels[counter] = this.pixels[x * this.height + y];
           }
       }

       this.pixels =  pixels;

       return true;
    }

    public boolean writeImage(String name) {
        BmpImage bmp = new BmpImage();
        bmp.image = new AbstractRgb888Image(this.width, this.height) {
            @Override
            public int getRgb888Pixel(int x, int y) {
                if(Image.this.grayscale) {
                    byte luminance = (byte) (Image.this.pixels[x * this.height + y] * 256);
                    return luminance * 0x1 + luminance * 0x100 + luminance * 0x10000;
                }
                else {
                    byte r = (byte) (Image.this.pixels[(x * this.height + y) * 3 + 0] * 256);
                    byte g = (byte) (Image.this.pixels[(x * this.height + y) * 3 + 1] * 256);
                    byte b = (byte) (Image.this.pixels[(x * this.height + y) * 3 + 2] * 256);
                    return r * 0x1 + g * 0x100 + b * 0x10000;
                }

            }

            double pixels[];
        };
        try {
            FileOutputStream out = new FileOutputStream(name + ".bmp");
            BmpWriter.write(out, bmp);

            out.close();
        }
        catch (IOException io){
            return false;
        }

        return true;
    }
    public boolean readImage(String name) {
        return false;
    }

    private int width;
    private int height;
    private boolean grayscale;
    private double[] pixels;
}

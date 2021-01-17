package Milkdromeda.Image;

import io.nayuki.bmpio.*;
import io.nayuki.bmpio.Rgb888Image;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Bitmap {
    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean setPixels(double[] pixels, boolean grayscale) {
        if(grayscale && pixels.length != this.width * this.height)
            return false;
        if(!grayscale && pixels.length != this.width * this.height * 3)
            return false;

        this.pixels = new double[pixels.length];
        for(int a = 0; a < pixels.length; a++)
            this.pixels[a] = pixels[a];

        this.grayscale = grayscale;

        return true;
    }
    public boolean setPixels(int[] pixels, boolean grayscale) {
        if(grayscale && pixels.length != this.width * this.height)
            return false;
        if(!grayscale && pixels.length != this.width * this.height * 3)
            return false;

        this.pixels = new double[pixels.length];
        for(int a = 0; a < pixels.length; a++)
            this.pixels[a] = pixels[a] / 256f;

        this.grayscale = grayscale;

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
        for(int a = 0; a < pixels.length; a+=3)
            pixels[a] = (this.pixels[a] + this.pixels[a + 1] + this.pixels[a + 2]) / 3;

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
                if(Bitmap.this.grayscale) {
                    byte luminance = (byte) (Bitmap.this.pixels[x * this.height + y] * 256);
                    return luminance << 0 | luminance << 8 | luminance << 16;
                }
                else {
                    int r = (int) (Bitmap.this.pixels[(x * this.height + y) * 3 + 0] * 256);
                    int g = (int) (Bitmap.this.pixels[(x * this.height + y) * 3 + 1] * 256);
                    int b = (int) (Bitmap.this.pixels[(x * this.height + y) * 3 + 2] * 256);
                    return b << 0| g << 8 | r << 16;
                }

            }
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
        BmpImage image;
        try {
            FileInputStream input = new FileInputStream(name + ".bmp");
            image = BmpReader.read(input);
            input.close();
        }
        catch (IOException e) {
            return false;
        }

        this.pixels = new double[3 * image.image.getWidth() * image.image.getHeight()];

        for(int x = 0; x < image.image.getWidth(); x++) {
            for(int y = 0; y < image.image.getHeight(); y++) {
                this.pixels[(x * image.image.getHeight() + y) * 3 + 0] = ((0x00ff0000 & image.image.getRgb888Pixel(x, y)) >> 16)  / 256f;
                this.pixels[(x * image.image.getHeight() + y) * 3 + 1] = ((0x0000ff00 & image.image.getRgb888Pixel(x, y)) >>  8) / 256f;
                this.pixels[(x * image.image.getHeight() + y) * 3 + 2] = ((0x000000ff & image.image.getRgb888Pixel(x, y)) >>  0) / 256f;
            }
        }

        this.grayscale = false;
        this.height = image.image.getHeight();
        this.width = image.image.getWidth();

        return false;
    }

    private int width;
    private int height;
    private boolean grayscale;
    private double[] pixels;
}

package Milkdromeda.Image;

import io.nayuki.bmpio.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Bitmap {
    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;

        this.pixels = null;
    }
    public Bitmap(Bitmap obj) {
        this.width = obj.width;
        this.height = obj.height;
        this.grayscale = obj.grayscale;

        if(obj.pixels != null) {
            this.pixels = new double[obj.pixels.length];

            for (int a = 0; a < this.pixels.length; a++)
                this.pixels[a] = obj.pixels[a];
        }
        else
            obj.pixels = null;
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
            this.pixels[a] = pixels[a] / 255f;

        this.grayscale = grayscale;

        return true;
    }
    public boolean setPixels(byte[] pixels, boolean grayscale) {
        int buffer[] = new int[pixels.length];

        for(int a = 0; a < buffer.length; a++)
            buffer[a] = 0xff & pixels[a];

        return this.setPixels(buffer, grayscale);
    }

    public double[] getPixels() {
        return this.pixels;
    }
    public double[] getPixel(int x, int y) {
        return new double[] {
          this.pixels[(x * this.height + y) * (this.grayscale ? 1 : 3) + (this.grayscale ? 0 : 0)],
          this.pixels[(x * this.height + y) * (this.grayscale ? 1 : 3) + (this.grayscale ? 0 : 1)],
          this.pixels[(x * this.height + y) * (this.grayscale ? 1 : 3) + (this.grayscale ? 0 : 2)]
        };
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
        if(this.grayscale || this.pixels == null)
            return;

        double pixels[] = new double[this.width * this.height];
        for(int a = 0; a < this.pixels.length; a+=3)
            pixels[a / 3] = (this.pixels[a] + this.pixels[a + 1] + this.pixels[a + 2]) / 3;

        this.grayscale = true;
        this.pixels = pixels;
    }
    public boolean crop(int x1, int y1, int x2, int y2) {
        if(this.pixels == null)
            return false;
       if(x1 > x2 || y1 > y2)
           return false;
       if(x2 >= this.width || y2 >= this.height)
           return false;

       double pixels[] = new double[(grayscale ? 1 : 3) *(x2 - x1) * (y2 - y1)];

       for(int x = x1, x_counter = 0; x < x2; x++, x_counter++) {
           for(int y = y1, y_counter = 0; y < y2; y++, y_counter++) {
               for(int a = 0; a < (this.grayscale ? 1 : 3); a++) {
                    int multiple = this.grayscale ? 1 : 3;
                   pixels[(x_counter * (y2 - y1) + y_counter) * multiple + a] = this.pixels[(x * this.height + y) * multiple + a];
               }
           }
       }

        this.width = x2 - x1;
        this.height = y2 - y1;

       this.pixels =  pixels;

       return true;
    }
    public boolean rotate() {

        return true;
    }
    public boolean rotateCounter() {
        double[] rotated = new double[this.pixels.length];

        for(int x = 0; x < this.height; x++) {
            for(int y = 0; y < this.width; y++) {
                //rotated[x * this.width + y] = this.pixels[]
            }
        }
        return true;
    }

    public boolean writeImage(String name) {
        if(this.pixels == null)
            return false;

        BmpImage bmp = new BmpImage();
        bmp.image = new AbstractRgb888Image(this.width, this.height) {
            @Override
            public int getRgb888Pixel(int x, int y) {
                if(Bitmap.this.grayscale) {
                    int luminance = (int) (Bitmap.this.pixels[x * this.height + y] * 255);
                    return luminance << 0 | luminance << 8 | luminance << 16;
                }
                else {
                    int r = (int) (Bitmap.this.pixels[(x * this.height + y) * 3 + 0] * 255);
                    int g = (int) (Bitmap.this.pixels[(x * this.height + y) * 3 + 1] * 255);
                    int b = (int) (Bitmap.this.pixels[(x * this.height + y) * 3 + 2] * 255);
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
                this.pixels[(x * image.image.getHeight() + y) * 3 + 0] = ((0x00ff0000 & image.image.getRgb888Pixel(x, y)) >> 16)  / 255f;
                this.pixels[(x * image.image.getHeight() + y) * 3 + 1] = ((0x0000ff00 & image.image.getRgb888Pixel(x, y)) >>  8) / 255f;
                this.pixels[(x * image.image.getHeight() + y) * 3 + 2] = ((0x000000ff & image.image.getRgb888Pixel(x, y)) >>  0) / 255f;
            }
        }

        this.grayscale = false;
        this.height = image.image.getHeight();
        this.width = image.image.getWidth();

        return false;
    }

    public boolean adjustCurve(Curve curve) {
        if(this.pixels == null)
            return false;

        for(int a = 0; a < this.pixels.length; a++)
            this.pixels[a] = curve.curve(this.pixels[a]);

        return true;
    }
    public static abstract class Curve {
        public abstract double curve(double input);
    }

    public boolean adjustLuminance(double blackLevel, double whiteLevel) {
        if(this.pixels == null)
            return false;

        double range = whiteLevel - blackLevel;
        for(int a = 0; a < this.pixels.length; a++) {
            this.pixels[a] = (this.pixels[a] - blackLevel) / range;

            if(this.pixels[a] < 0)
                this.pixels[a] = 0.0;
            else if(this.pixels[a] > whiteLevel)
                this.pixels[a] = 1.0;
        }

        return true;
    }

    private int width;
    private int height;
    private boolean grayscale;
    private double[] pixels;
}

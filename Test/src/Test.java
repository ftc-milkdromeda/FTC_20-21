import Milkdromeda.Image.Bitmap;

import java.nio.ByteBuffer;

public class Test {
    private static int countPixel(Bitmap image, double lowerBound, double upperBound) {
        image.makeGrayscale();

        double pixels[] = image.getPixels();
        int numOfPixel = 0;

        if(pixels == null)
            return 0;

        for(int a = 0; a < pixels.length; a++) {
            numOfPixel += (pixels[a] > lowerBound && pixels[a] < upperBound) ? 1 : 0;
        }

        return numOfPixel;
    }

    public static void main(String [] args) {
        Bitmap bitmap0 = new Bitmap(500, 500);
        Bitmap bitmap1 = new Bitmap(500, 500);
        Bitmap bitmap2 = new Bitmap(500, 500);

        bitmap0.readImage("image");
        bitmap1.readImage("bitmap1");
        bitmap2.readImage("bitmap2");

        bitmap0.makeGrayscale();
        bitmap1.makeGrayscale();
        bitmap2.makeGrayscale();

        final int sampleSize = 100;
        final int bl_x = 540;
        final int wl_x = 100;

        final int bl_y = 590;
        final int wl_y = 730;

        double bl_0 = 0;
        double bl_1 = 0;
        double bl_2 = 0;

        double wl_0 = 0;
        double wl_1 = 0;
        double wl_2 = 0;

        for(int a = 0; a < sampleSize; a++) {
            bl_0 += bitmap0.getPixel(bl_x, bl_y + a)[0];
            bl_1 += bitmap1.getPixel(bl_x, bl_y + a)[0];
            bl_2 += bitmap2.getPixel(bl_x, bl_y + a)[0];

            wl_0 += bitmap0.getPixel(wl_x + a, wl_y)[0];
            wl_1 += bitmap1.getPixel(wl_x + a, wl_y)[0];
            wl_2 += bitmap2.getPixel(wl_x + a, wl_y)[0];
        }

        bl_0 /= sampleSize;
        bl_1 /= sampleSize;
        bl_2 /= sampleSize;

        wl_0 /= sampleSize;
        wl_1 /= sampleSize;
        wl_2 /= sampleSize;

        bitmap0.crop(84, 436, 234, 696);
        bitmap1.crop(84, 436, 234, 696);
        bitmap2.crop(84, 436, 234, 696);

        bitmap0.adjustLuminance(bl_0, wl_0);
        bitmap1.adjustLuminance(bl_1, wl_1);
        bitmap2.adjustLuminance(bl_2, wl_2);

/*
        byte buffer[] = new byte[500 * 500 * 3];
        for(int x = 0; x < 500; x++) {
            for(int y = 0; y < 500; y++) {
                buffer[(x * 500 + y) * 3 + 0] = 127;
                buffer[(x * 500 + y) * 3 + 1] = -100;
                buffer[(x * 500 + y) * 3 + 2] = -1;
            }
        }

        System.out.println(bitmap.setPixels(buffer, false));
/**/

        Bitmap.Curve curve = new Bitmap.Curve() {
            @Override
            public double curve(double input) {
                if(input > 0.475)
                    return 1.0;
                else
                    return 0.0;
            }
        };
        bitmap0.adjustCurve(curve);
        bitmap1.adjustCurve(curve);
        bitmap2.adjustCurve(curve);

        System.out.println(Test.countPixel(bitmap0, -0.1, 0.1));
        System.out.println(Test.countPixel(bitmap1, -0.1, 0.1));
        System.out.println(Test.countPixel(bitmap2, -0.1, 0.1));

        bitmap0.writeImage("output0");
        bitmap1.writeImage("output1");
        bitmap2.writeImage("output2");
    }
}


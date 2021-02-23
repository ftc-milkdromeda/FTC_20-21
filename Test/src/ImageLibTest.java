import Milkdromeda.Image.Bitmap;

import java.nio.ByteBuffer;

public class ImageLibTest {
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
        Bitmap image = new Bitmap(300, 300);
        image.readImage("input");


        image.makeGrayscale();

        image.writeImage("/storage/self/primary/FIRST/beforeImage");

        double whiteLevel = 0;
        double blackLevel = 0;

        for(int a = 0; a < LuminanceSampleSize; a++) {
            blackLevel += image.getPixel(BlackLevelCoord[0], BlackLevelCoord[1] + a)[0];
            whiteLevel += image.getPixel(WhiteLevelCoord[0] + a, WhiteLevelCoord[1])[0];

            System.out.println("A Value: " + a);
        }

        whiteLevel /= LuminanceSampleSize;
        blackLevel /= LuminanceSampleSize;

        image.crop(crop_x1, crop_y1, crop_x2, crop_y2);

       //image.adjustLuminance(blackLevel, whiteLevel);

        Bitmap.Curve curve = new Bitmap.Curve() {
            @Override
            public double curve(double input) {
                if(input > 0.6)
                    return 1.0;
                else
                    return 0.0;
            }
        };

        //image.adjustCurve(curve);

        image.writeImage("output");
    }

    private static final int oneRingMin = 800;
    private static final int fourRingMin = 13000;

    private static final int crop_x1 = 81;
    private static final int crop_x2 = 251;

    private static final int crop_y1 = 614;
    private static final int crop_y2 = 978;

    private static final int LuminanceSampleSize = 50;
    private static final int BlackLevelCoord[] = {525, 655};
    private static final int WhiteLevelCoord[] = {123, 927};
}


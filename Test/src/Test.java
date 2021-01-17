import Milkdromeda.Image.Bitmap;

public class Test {
    public static void main(String [] args) {
        Bitmap bitmap = new Bitmap(500, 500);

        bitmap.readImage("bitmap");
/*
        int buffer[] = new int[500 * 500 * 3];
        for(int x = 0; x < 500; x++) {
            for(int y = 0; y < 500; y++) {
                buffer[(x * 500 + y) * 3 + 0] = 0;
                buffer[(x * 500 + y) * 3 + 1] = 0;
                buffer[(x * 500 + y) * 3 + 2] = 255;
            }
        }

        bitmap.setPixels(buffer, false);
/**/
        bitmap.writeImage("output");
    }
}


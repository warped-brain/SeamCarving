import java.awt.image.BufferedImage;

public class imageOperations {
    public static BufferedImage toGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                int avg = (r + g + b) / 3;
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                image.setRGB(x, y, p);
            }
        }
        return image;
    }

    public static int sobelFilter(BufferedImage image, int row, int col, int gray) {
        int gx = 0, gy = 0;
        int[][] sobel_x = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobel_y = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = col + j;
                int y = row + i;
                if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
                    continue;
                }
                gx += (image.getRGB(x, y) & 0xff) * sobel_x[i + 1][j + 1];
                gy += (image.getRGB(x, y) & 0xff) * sobel_y[i + 1][j + 1];
            }
        }
        return (int) Math.abs(gx) + Math.abs(gy);
    }
    public static BufferedImage getSobelImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage sobelImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        image = toGrayscale(image);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Get the grayscale value of the current pixel
                int gray = image.getRGB(col, row) & 0xff;
                // Apply the Sobel filter to the grayscale value
                int value = sobelFilter(image, row, col, gray);
                // Set the new value as the pixel in the Sobel image
                int newPixel = (value << 24) | (value << 16) | (value << 8) | value;
                sobelImage.setRGB(col, row, newPixel);
            }
        }
        return sobelImage;
    }
    public static int[][] applySobelMatrix(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] sobelValues = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Get the grayscale value of the current pixel
                int gray = image.getRGB(col, row) & 0xff;
                // Apply the Sobel filter to the grayscale value
                sobelValues[row][col] = sobelFilter(image, row, col, gray);
            }
        }
        return sobelValues;
    }
    public static int[][] imageToMatrix(BufferedImage im){
        int [][] image = new int[im.getHeight()][im.getWidth()];
        for (int i = 0; i < im.getHeight(); i++) {
            for (int j = 0; j < im.getWidth(); j++) {
                image[i][j] = im.getRGB(j, i);
            }
        }
        return image;
    }
    public static BufferedImage matrixToImage(int[][] matrix){
        int height = matrix.length;
        int width = matrix[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i,matrix[i][j]);
            }
        }
        return image;
    }
}

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SeamCarving {

    public static int[] minSeamFinder(int[][] image) {
        int rows = image.length;
        int columns = image[0].length;
        int[][] energy = new int[rows][columns];
        int[][] dp = new int[rows][columns];
        int[][] seam = new int[rows][columns];
        int[] minSeam = new int[rows];

        // fill energy array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int weight = image[i][j];
                if (i == 0) {
                    dp[i][j] = weight;
                } else {
                    dp[i][j] = Integer.MAX_VALUE;
                }
                energy[i][j] = weight;
            }
        }

        // compute shortest path
        for (int i = 1; i < rows; i++) {
            for (int j= 0; j < columns; j++) {
                if (j > 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + energy[i][j]);
                    if(dp[i][j] == dp[i - 1][j - 1] + energy[i][j]) {
                        seam[i][j] = j-1;
                    }
                }
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + energy[i][j]);
                if(dp[i][j] == dp[i - 1][j] + energy[i][j]) {
                    seam[i][j] = j;
                }
                if (j < columns - 1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j + 1] + energy[i][j]);
                    if(dp[i][j] == dp[i - 1][j + 1] + energy[i][j]) {
                        seam[i][j] = j+1;
                    }
                }
            }
        }

        // find last column of seam
        int minIndex = 0;
        for (int i = 1; i < columns; i++) {
            if (dp[rows - 1][i] < dp[rows - 1][minIndex]) {
                minIndex = i;
            }
        }
        minSeam[rows - 1] = minIndex;
        for(int i = rows - 2; i >= 0; i--) {
            minSeam[i] = seam[i+1][minSeam[i+1]];
        }

        return minSeam;
    }

    public static BufferedImage seamMarker(BufferedImage image, int newWidth) {
        int [][] sobelImage = imageOperations.applySobelMatrix(image);
        int rows = sobelImage.length;
        int columns = sobelImage[0].length;
        int [] minSeam;
        for (int i = 1; i <= columns-newWidth ; i++) {
            minSeam = minSeamFinder(sobelImage);
            for (int j = 0; j < rows; j++) {
                image.setRGB(minSeam[j],j, Color.MAGENTA.getRGB());
            }

            // remove seam from Sobel Image and update it
            for (int j = 0; j < rows; j++) {
                for (int k = minSeam[j]; k < columns - i; k++) {
                    sobelImage[j][k] = sobelImage[j][k + 1];
                }
            }
            int[][] newSobelImage = new int[rows][columns-i];
            for (int j = 0; j < rows; j++) {
                newSobelImage[j]=Arrays.copyOf(sobelImage[j],columns-i);
            }
            sobelImage = newSobelImage;
        }
        return image;
    }

    public static BufferedImage seamRemover(BufferedImage image, int newWidth) {
        int [][] sobelImage = imageOperations.applySobelMatrix(image);
        int[][] imageMatrix = imageOperations.imageToMatrix(image);
        int rows = sobelImage.length;
        int columns = sobelImage[0].length;
        int [] minSeam;
        for (int i = 0; i <= columns-newWidth ; i++) {
            minSeam = minSeamFinder(sobelImage);

            // remove seam from Sobel Image and update it
            for (int j = 0; j < rows; j++) {
                for (int k = minSeam[j]; k < columns -1-i; k++) {
                    sobelImage[j][k] = sobelImage[j][k + 1];
                    imageMatrix[j][k] = imageMatrix[j][k + 1];
                }
            }
            int[][] newSobelImage = new int[rows][columns-i];
            int[][] newImageMatrix = new int[rows][columns-i];
            for (int j = 0; j < rows; j++) {
                newSobelImage[j]=Arrays.copyOf(sobelImage[j],columns-i);
                newImageMatrix[j]=Arrays.copyOf(imageMatrix[j],columns-i);
            }
            imageMatrix = newImageMatrix;
            sobelImage = newSobelImage;
        }
        return imageOperations.matrixToImage(imageMatrix);
    }
}
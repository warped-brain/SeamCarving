import java.awt.image.BufferedImage;

public class adjacencyMatrix {
    public static SparseMatrix createAdjacencyMatrix(BufferedImage sobelImage){
        // Apply Sobel filter to the image
        int width=sobelImage.getWidth();
        int height=sobelImage.getHeight();
        int numNodes = width*height;
        SparseMatrix adjacencyMatrix=new SparseMatrix(height,width,4*numNodes);

        for(int row=0;row<height; row++){
            for(int col=0;col<width; col++){
                int currentNode=row*width+col;
//                System.out.println(currentNode);
                int weight=sobelImage.getRGB(col,row)&0xff;
                // Check the right node
                if(col<width -1){
                    int rightNode=currentNode+1;
                    adjacencyMatrix.setValue(currentNode,rightNode,weight);
                }
                // Check the bottom node
                if(row<height -1){
                    int bottomNode=currentNode+width;
//                    int weight=sobelImage.getRGB(col,row)&0xff;
                    adjacencyMatrix.setValue(currentNode,bottomNode,weight);
                }
                // Check the left node
                if(col>0){
                    int leftNode=currentNode-1;
//                    int weight=sobelImage.getRGB(col,row)&0xff;
                    adjacencyMatrix.setValue(currentNode,leftNode,weight);
                }
                // Check the top node
                if(row>0){
                    int topNode=currentNode-width;
//                    int weight=sobelImage.getRGB(col,row)&0xff;
                    adjacencyMatrix.setValue(currentNode,topNode,weight);
                }

            }
        }
        return adjacencyMatrix;
    }

    public static BufferedImage getSobelImage(BufferedImage image) {
        BufferedImage sobelImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        int[][] gx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] gy = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        for (int row = 1; row < image.getHeight() - 1; row++) {
            for (int col = 1; col < image.getWidth() - 1; col++) {
                int newPixel = (int) Math.sqrt(convolve(image, row, col, gx) + convolve(image, row, col, gy));
                sobelImage.setRGB(col, row, newPixel);
            }
        }
        return sobelImage;
    }

    private static int convolve(BufferedImage image, int row, int col, int[][] kernel) {
        int total = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int pixel = image.getRGB(col + j, row + i);
                int gray = (pixel >> 16) & 0xff;
                total += gray * kernel[i + 1][j + 1];
            }
        }
        return Math.abs(total);
    }
    public static BufferedImage getImageFromMatrix(SparseMatrix matrix) {
        int width = matrix.getNumCols();
        int height = matrix.getNumRows();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int value = matrix.getValue(row, col);
                int pixel = (value << 16) | (value << 8) | value;
                image.setRGB(col, row, pixel);
            }
        }
        return image;
    }

}

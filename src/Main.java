import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) throws IOException {

        String imagePath = "./temple.jpg";
        BufferedImage im =ImageIO.read(new File(imagePath));
        System.out.printf("Image:\n Original width: %s \n Original Height: %s \n",im.getWidth(),im.getHeight());
        System.out.print("Enter new width: ");
        Scanner s = new Scanner(System.in);

        int newWidth = s.nextInt();

        BufferedImage greyImage = imageOperations.toGrayscale(im);
        File greyFile = new File("./greyImage.jpg");
        ImageIO.write(greyImage,"jpg",greyFile);

        BufferedImage sobelImage = imageOperations.getSobelImage(im);
        File sobelFile = new File("./sobelImage.jpg");
        ImageIO.write(sobelImage,"jpg",sobelFile);

        BufferedImage seamRemoved = SeamCarving.seamRemover(im,newWidth);
        File seamRemovedImage = new File("./seamRemoved.jpg");
        ImageIO.write(seamRemoved,"jpg",seamRemovedImage);
        System.out.printf("\n Carved Image:\n Seam carved width: %s \n Seam carved Height: %s",seamRemoved.getWidth(),seamRemoved.getHeight());


        BufferedImage seamMarked = SeamCarving.seamMarker(im,newWidth);
        File seamMarkedImage = new File("./seamMarked.jpg");
        ImageIO.write(seamMarked,"jpg",seamMarkedImage);

    }
}

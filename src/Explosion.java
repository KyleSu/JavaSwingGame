import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class Explosion extends GameObj{
    public static final String img_file = "Explosion.png";
    public static final int SIZE = 20;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public int imgSequenceNo;
    public TreeMap<Integer, TreeMap<Character,Integer>> imgArray;
    //Maps each image of an explosion (number) to a x-int and y-int value

    private static BufferedImage img;

    public Explosion(int courtWidth, int courtHeight, int xposition, int 
            yposition) {
        super(INIT_VEL_X, INIT_VEL_Y, xposition, yposition, SIZE, SIZE, 
                courtWidth, courtHeight);

        imgArray = new TreeMap<Integer, TreeMap<Character,Integer>>();
        for (int i = 1; i <= 17; i++) {

            TreeMap<Character, Integer> toAdd = new TreeMap<Character, 
                    Integer>();

            /*based on the imgSequenceNo, retrieve proper location of subimages
             * from explosions.png
             */
            if (i > 1 && i <= 5) { // first row
                toAdd.put('x', ((i-1) * 96));
                toAdd.put('y', 0);
                imgArray.put(i, toAdd);
            } else if (i > 5 && i <= 10) { // second row
                toAdd.put('x', ((i-6) * 96));
                toAdd.put('y', 96);
                imgArray.put(i, toAdd);
            } else if (i >10 && i <= 15) { // third row
                toAdd.put('x', ((i-11) * 96));
                toAdd.put('y', 96*2);
                imgArray.put(i, toAdd);
            } else if (i > 15 && i <= 17) { // fourth row
                toAdd.put('x', ((i-16) * 96));
                toAdd.put('y', 96*3);
                imgArray.put(i, toAdd);
            }
        }

        try {
            if (img == null) { // read in file if img is null
                img = ImageIO.read(new File(img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }


    }

    @Override
    public void draw(Graphics g) {
        if (imgArray.containsKey(imgSequenceNo)) {
            int x = (imgArray.get(imgSequenceNo)).get('x');
            int y = (imgArray.get(imgSequenceNo)).get('y');
            // Pull out subimage from x,y coords and square of width 96
            BufferedImage toDisplay = img.getSubimage(x, y, 96, 96);
            // Draw subimage
            g.drawImage(toDisplay, pos_x, pos_y, width, height, null);
        }
    }
}

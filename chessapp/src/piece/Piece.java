package piece;

import Main.Board;

import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.*;

public class Piece {

    public BufferedImage image;
    public int x, y;
    public int col, row, prevCol, prevRow;
    public int color;

    public Piece(int color, int col, int row){

        this.color = color;
        this.col = col;
        this.row = row;
        x = getX(col);
        y = getY(row);
        prevCol = col;
        prevRow = row;
    }

    //buffer image
    public BufferedImage getImage(String imagePath){

        BufferedImage image = null;

        try{
            System.out.println(imagePath + ".png");
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            System.out.println(imagePath + ".png");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("image cant be loaded");
        }
        return image;

    }

    public int getX(int col){
        return col * Board.SQUARE_SIZE;
    }
    public int getY(int row){
        return row * Board.SQUARE_SIZE;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}

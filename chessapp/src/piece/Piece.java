package piece;

import Main.Board;
import Main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.IOException;
import java.io.InputStream;
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

        final String fullPath = imagePath + ".png";

        try(InputStream imageStream = getClass().getResourceAsStream(fullPath)){
            if(imageStream == null){
                System.err.println("Missing image resource: " + fullPath);
                return createFallbackImage();
            }
            return ImageIO.read(imageStream);
        }catch(IOException e){
            System.err.println("Failed to load image resource: " + fullPath);
            e.printStackTrace();
            return createFallbackImage();
        }
    }

    private BufferedImage createFallbackImage(){
        BufferedImage fallback = new BufferedImage(Board.SQUARE_SIZE, Board.SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = fallback.createGraphics();
        g2.setColor(Color.MAGENTA);
        g2.fillRect(0, 0, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
        g2.dispose();
        return fallback;
    }

    public int getX(int col){
        return col * Board.SQUARE_SIZE;
    }
    public int getY(int row){
        return row * Board.SQUARE_SIZE;
    }
    public int getCol(int x){
        return (x + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }
    public int getRow(int y){
        return (y + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
    }

    public void updatePosition(){
        x = getX(col);
        y = getY(row);
        prevCol = col;
        prevRow = row;
    }

    public void resetPosition(){
        col = prevCol;
        row = prevRow;
        x = getX(col);
        y = getY(row);
    }

    public boolean canMove(int targetCol, int targetRow){
        return false;
    }

    public boolean isWithinBoard(int targetCol, int targetRow){
        return targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <= 7;
    }

    public boolean isSameSquare(int targetCol, int targetRow){
        return targetCol == prevCol && targetRow == prevRow;
    }

    // Returns the piece at the target square, or null if empty
    public Piece hittingPiece(int targetCol, int targetRow){
        for(Piece piece : GamePanel.simPieces){
            if(piece.col == targetCol && piece.row == targetRow && piece != this){
                return piece;
            }
        }
        return null;
    }

    // Check if target square is valid (not occupied by same color piece)
    public boolean isValidSquare(int targetCol, int targetRow){
        Piece hittingP = hittingPiece(targetCol, targetRow);
        if(hittingP == null){
            return true; // empty square
        }
        else{
            if(hittingP.color != this.color){
                return true; // can capture
            }
            else{
                return false; // blocked by own piece
            }
        }
    }

    // Check if any piece is blocking the straight-line path
    public boolean pieceIsOnStraightLine(int targetCol, int targetRow){
        // Moving left or right
        if(targetRow == prevRow){
            int step = (targetCol > prevCol) ? 1 : -1;
            for(int c = prevCol + step; c != targetCol; c += step){
                if(hittingPiece(c, prevRow) != null){
                    return true;
                }
            }
        }
        // Moving up or down
        if(targetCol == prevCol){
            int step = (targetRow > prevRow) ? 1 : -1;
            for(int r = prevRow + step; r != targetRow; r += step){
                if(hittingPiece(prevCol, r) != null){
                    return true;
                }
            }
        }
        return false;
    }

    // Check if any piece is blocking the diagonal path
    public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow){
        if(Math.abs(targetCol - prevCol) == Math.abs(targetRow - prevRow)){
            int colStep = (targetCol > prevCol) ? 1 : -1;
            int rowStep = (targetRow > prevRow) ? 1 : -1;
            int c = prevCol + colStep;
            int r = prevRow + rowStep;
            while(c != targetCol && r != targetRow){
                if(hittingPiece(c, r) != null){
                    return true;
                }
                c += colStep;
                r += rowStep;
            }
        }
        return false;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}


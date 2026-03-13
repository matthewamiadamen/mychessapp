package piece;

import Main.GamePanel;

public class Knight extends Piece{

    public Knight(int color, int col, int row){
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("/piece/WhiteKnight");
        }
        else{
            image = getImage("/piece/BlackKnight");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow)){
            // L-shape: 2 squares in one direction, 1 in the other
            int colDiff = Math.abs(targetCol - prevCol);
            int rowDiff = Math.abs(targetRow - prevRow);
            if((colDiff == 2 && rowDiff == 1) || (colDiff == 1 && rowDiff == 2)){
                if(isValidSquare(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
}
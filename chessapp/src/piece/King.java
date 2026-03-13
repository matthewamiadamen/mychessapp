package piece;

import Main.GamePanel;

public class King extends Piece{

    public King(int color, int col, int row){
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("/piece/WhiteKing");
        }
        else{
            image = getImage("/piece/BlackKing");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow)){
            // 1 square in any direction
            int colDiff = Math.abs(targetCol - prevCol);
            int rowDiff = Math.abs(targetRow - prevRow);
            if(colDiff <= 1 && rowDiff <= 1 && !(colDiff == 0 && rowDiff == 0)){
                if(isValidSquare(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
}
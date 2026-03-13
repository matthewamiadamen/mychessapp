package piece;

import Main.GamePanel;

public class Bishop extends Piece{

    public Bishop(int color, int col, int row){
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("/piece/WhiteBishop");
        }
        else{
            image = getImage("/piece/BlackBishop");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            if(Math.abs(targetCol - prevCol) == Math.abs(targetRow - prevRow)){
                if(isValidSquare(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
}
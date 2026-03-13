package piece;

import Main.GamePanel;

public class Queen extends Piece{

    public Queen(int color, int col, int row){
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("/piece/WhiteQueen");
        }
        else{
            image = getImage("/piece/BlackQueen");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            // Straight line (rook-like)
            if(targetCol == prevCol || targetRow == prevRow){
                if(isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)){
                    return true;
                }
            }
            // Diagonal (bishop-like)
            if(Math.abs(targetCol - prevCol) == Math.abs(targetRow - prevRow)){
                if(isValidSquare(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
}
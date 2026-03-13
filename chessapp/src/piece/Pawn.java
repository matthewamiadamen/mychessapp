package piece;

import Main.GamePanel;

public class Pawn extends Piece{

    public Pawn(int color, int col, int row){
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("/piece/WhitePawn");
        }
        else{
            image = getImage("/piece/BlackPawn");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            // Movement direction: white moves up (-1), black moves down (+1)
            int moveDir = (color == GamePanel.WHITE) ? -1 : 1;

            // 1 square forward
            if(targetCol == prevCol && targetRow == prevRow + moveDir){
                if(hittingPiece(targetCol, targetRow) == null){
                    return true;
                }
            }
            // 2 squares forward from starting row
            if(targetCol == prevCol && targetRow == prevRow + moveDir * 2){
                if((color == GamePanel.WHITE && prevRow == 6) || (color == GamePanel.BLACK && prevRow == 1)){
                    if(hittingPiece(targetCol, targetRow) == null && hittingPiece(targetCol, prevRow + moveDir) == null){
                        return true;
                    }
                }
            }
            // Diagonal capture
            if(Math.abs(targetCol - prevCol) == 1 && targetRow == prevRow + moveDir){
                if(hittingPiece(targetCol, targetRow) != null && hittingPiece(targetCol, targetRow).color != this.color){
                    return true;
                }
            }
        }
        return false;
    }

}


package Main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;

import java.awt.Graphics2D;
import java.util.ArrayList;

//game screen
public class GamePanel extends JPanel implements Runnable{

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    //how often it refreshes, gamethread is what everything runs on updates to
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    //pieces is backup for simpieces if we want to reset
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();

    //the piece being dragged
    Piece activePiece;

    //color
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    //window where game lies
    public GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        setPieces();
        //will copy pieces to history
        copyPieces(pieces, simPieces);
    }

    //calls run method
    public void launchGame(){
        gameThread = new Thread(this);
        gameThread.start();
        //board generated when game launches
    }

    public void setPieces(){
        // White Pawns
        pieces.add(new Pawn(WHITE, 0, 6));
        pieces.add(new Pawn(WHITE, 1, 6));
        pieces.add(new Pawn(WHITE, 2, 6));
        pieces.add(new Pawn(WHITE, 3, 6));
        pieces.add(new Pawn(WHITE, 4, 6));
        pieces.add(new Pawn(WHITE, 5, 6));
        pieces.add(new Pawn(WHITE, 6, 6));
        pieces.add(new Pawn(WHITE, 7, 6));

        // White Major Pieces
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Knight(WHITE, 1, 7));
        pieces.add(new Knight(WHITE, 6, 7));
        pieces.add(new Bishop(WHITE, 2, 7));
        pieces.add(new Bishop(WHITE, 5, 7));
        pieces.add(new Queen(WHITE, 3, 7));
        pieces.add(new King(WHITE, 4, 7));

        // Black Pawns
        pieces.add(new Pawn(BLACK, 0, 1));
        pieces.add(new Pawn(BLACK, 1, 1));
        pieces.add(new Pawn(BLACK, 2, 1));
        pieces.add(new Pawn(BLACK, 3, 1));
        pieces.add(new Pawn(BLACK, 4, 1));
        pieces.add(new Pawn(BLACK, 5, 1));
        pieces.add(new Pawn(BLACK, 6, 1));
        pieces.add(new Pawn(BLACK, 7, 1));

        // Black Major Pieces
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 7, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));
    }
    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target){

        target.clear();
        for(int i = 0; i < source.size(); i++){
            target.add(source.get(i));
        }

    }

    @Override
    public void run(){
        //updates and repaints
        //as long as game running and time tracked all these processes happen
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            if(delta >= 1){
                update(); //does it 60 times a second
                repaint(); // calls paint component
                delta --;
            }
        }
    }

    //call both 60 fps in loop
    //update piece information x and y positions and number pieces left
    private void update(){

        // MOUSE BUTTON PRESSED
        if(mouse.pressed){
            if(activePiece == null){
                // Pick up a piece
                for(Piece piece : simPieces){
                    if(piece.color == currentColor &&
                       piece.col == mouse.x / Board.SQUARE_SIZE &&
                       piece.row == mouse.y / Board.SQUARE_SIZE){
                        activePiece = piece;
                        break;
                    }
                }
            }
            else{
                // Dragging - piece follows mouse
                activePiece.x = mouse.x - Board.HALF_SQUARE_SIZE;
                activePiece.y = mouse.y - Board.HALF_SQUARE_SIZE;
                activePiece.col = activePiece.getCol(activePiece.x);
                activePiece.row = activePiece.getRow(activePiece.y);
            }
        }

        // MOUSE BUTTON RELEASED
        if(!mouse.pressed){
            if(activePiece != null){
                if(activePiece.canMove(activePiece.col, activePiece.row)){

                    // Capture: remove the opponent piece if present
                    Piece capturedPiece = activePiece.hittingPiece(activePiece.col, activePiece.row);
                    if(capturedPiece != null){
                        simPieces.remove(capturedPiece);
                    }

                    // Confirm position
                    activePiece.updatePosition();

                    // Switch turn
                    if(currentColor == WHITE){
                        currentColor = BLACK;
                    } else {
                        currentColor = WHITE;
                    }
                }
                else{
                    // Invalid move: revert
                    activePiece.resetPosition();
                }
                activePiece = null;
            }
        }
    }

    //handles drawing chessboard, chess pieces and on screen messages etc
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //converts g to 2d graphic
        Graphics2D g2 = (Graphics2D)g;
        //paints initial board
        board.draw(g2);

        //pieces
        for(Piece p : simPieces){
            p.draw(g2);
        }

        // Draw active piece on top while dragging
        if(activePiece != null){
            // Highlight the original square
            g2.setColor(new Color(255, 255, 0, 100));
            g2.fillRect(activePiece.prevCol * Board.SQUARE_SIZE,
                        activePiece.prevRow * Board.SQUARE_SIZE,
                        Board.SQUARE_SIZE, Board.SQUARE_SIZE);

            // Draw the dragged piece on top
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            activePiece.draw(g2);
        }

        // Turn indicator on the right side panel
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        if(currentColor == WHITE){
            g2.drawString("White's Turn", 850, 400);
        } else {
            g2.drawString("Black's Turn", 850, 400);
        }
    }

}


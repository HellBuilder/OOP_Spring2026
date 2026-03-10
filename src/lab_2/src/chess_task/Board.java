package chess_task;
import java.util.ArrayList;

public class Board {

    private ArrayList<Piece> pieces = new ArrayList<>();

    public void addPiece(Piece p) {
        pieces.add(p);
    }

    public Piece getPieceAt(Position pos) {
        for (Piece p : pieces) {
            if (p.getPosition().x == pos.x &&
                p.getPosition().y == pos.y) {
                return p;
            }
        }
        return null;
    }

    
    
    
    public boolean isEmpty(Position pos) {
        return getPieceAt(pos) == null;
    }
    
    
    
    
    

    public boolean movePiece(Piece p, Position dest) {

        if (p.isLegalMove(dest, this)) {

            Piece target = getPieceAt(dest);

            if (target != null)
                pieces.remove(target);
            p.setPosition(dest);
            return true;
        }
        return false;
    }
    
    
    
    
    

    public void printBoard() {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                Piece p = getPieceAt(new Position(x,y));
                if (p == null)
                    System.out.print(". ");
                else
                    System.out.print(p.getClass().getSimpleName().charAt(0) + " ");
            }
            System.out.println();
        }
    }
}
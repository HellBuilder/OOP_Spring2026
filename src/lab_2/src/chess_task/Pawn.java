package chess_task;

public class Pawn extends Piece {
	
	int dir;
	private boolean firstMove = true;
    public Pawn(Position pos, boolean white) {
        super(pos, white);
        
        
        if (white == true) {
        	this.dir = 1;
        }
        else {
        	this.dir = -1;
        }

    }

    @Override
    public boolean isLegalMove(Position dest, Board board) {
    	
    	int dx = dest.x - pos.x;
        int dy = dest.y - pos.y;
        
        if (dest.x == pos.x &&
            dest.y == pos.y + dir &&
            board.isEmpty(dest))
            return true;
        if (firstMove && dx == 0 && dy == 2 * dir) {

            Position mid = new Position(pos.x, pos.y + dir);

            if (board.isEmpty(mid) && board.isEmpty(dest)) {
                firstMove = false;
                return true;
            }
        }

        if (Math.abs(dx) == 1 && dy == dir) {

            Piece target = board.getPieceAt(dest);

            if (target != null && target.isWhite() != white) {
                firstMove = false;
                return true;
            }
        }
        return false;
    }
}
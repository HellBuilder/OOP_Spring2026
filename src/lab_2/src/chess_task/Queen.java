package chess_task;

public class Queen extends Piece {

    public Queen(Position pos, boolean white) {
        super(pos, white);
    }

    @Override
    public boolean isLegalMove(Position dest, Board board) {

        int dx = Math.abs(dest.x - pos.x);
        int dy = Math.abs(dest.y - pos.y);

        if (dx == dy) {
            Bishop temp = new Bishop(pos,white);
            return temp.isLegalMove(dest,board);
        }

        if (pos.x == dest.x || pos.y == dest.y) {
            Rook temp = new Rook(pos,white);
            return temp.isLegalMove(dest,board);
        }

        return false;
    }
}
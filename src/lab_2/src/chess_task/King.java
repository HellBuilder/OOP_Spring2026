package chess_task;

public class King extends Piece {

    public King(Position pos, boolean white) {
        super(pos, white);
    }

    @Override
    public boolean isLegalMove(Position dest, Board board) {

        int dx = Math.abs(dest.x - pos.x);
        int dy = Math.abs(dest.y - pos.y);

        return dx <= 1 && dy <= 1 && (dx + dy > 0);
    }
}
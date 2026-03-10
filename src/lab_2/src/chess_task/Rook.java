package chess_task;

public class Rook extends Piece {

    public Rook(Position pos, boolean white) {
        super(pos, white);
    }

    @Override
    public boolean isLegalMove(Position dest, Board board) {

        if (pos.x != dest.x && pos.y != dest.y)
            return false;

        int dx = Integer.compare(dest.x, pos.x);
        int dy = Integer.compare(dest.y, pos.y);

        int x = pos.x + dx;
        int y = pos.y + dy;

        while (x != dest.x || y != dest.y) {

            if (!board.isEmpty(new Position(x,y)))
                return false;

            x += dx;
            y += dy;
        }

        return true;
    }
}
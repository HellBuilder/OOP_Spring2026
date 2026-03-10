package chess_task;

public class Bishop extends Piece {

    public Bishop(Position pos, boolean white) {
        super(pos, white);
    }

    @Override
    public boolean isLegalMove(Position dest, Board board) {

        int dx = dest.x - pos.x;
        int dy = dest.y - pos.y;

        if (Math.abs(dx) != Math.abs(dy))
            return false;

        dx = Integer.compare(dx,0);
        dy = Integer.compare(dy,0);

        int x = pos.x + dx;
        int y = pos.y + dy;

        while (x != dest.x) {

            if (!board.isEmpty(new Position(x,y)))
                return false;

            x += dx;
            y += dy;
        }

        return true;
    }
}
package chess_task;

public abstract class Piece {

    protected Position pos;
    protected boolean white;

    public Piece(Position pos, boolean white) {
        this.pos = pos;
        this.white = white;
    }

    public Position getPosition() {
        return pos;
    }

    public boolean isWhite() {
        return white;
    }

    public void setPosition(Position p) {
        pos = p;
    }

    public abstract boolean isLegalMove(Position dest, Board board);
}
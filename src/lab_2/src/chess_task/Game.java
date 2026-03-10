package chess_task;

public class Game {

    public static void main(String[] args) {

        Board board = new Board();

        Piece rook = new Rook(new Position(0,0), true);
        Piece bishop = new Bishop(new Position(2,0), true);
        Piece knight = new Knight(new Position(1,0), true);
        Piece rook2 = new Rook(new Position(7,0), true);
        
        board.addPiece(rook);
        board.addPiece(rook2);
        board.addPiece(bishop);
        board.addPiece(knight);

        board.printBoard();

        System.out.println("Move rook to (0,5)");

        boolean moved = board.movePiece(rook, new Position(0,5));

        System.out.println("Move success: " + moved);

        board.printBoard();
    }
}
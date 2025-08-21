package morris;

import java.util.List;
public abstract class MoveGenerator {
    public abstract List<Board> generateMoves(Board board, boolean isWhite);
}
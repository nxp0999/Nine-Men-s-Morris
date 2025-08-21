package morris;

import java.util.*;
public class OpeningMoveGenerator extends MoveGenerator {
    public List<Board> generateMoves(Board board, boolean isWhite) {
        return generateAdd(board, isWhite);
    }

    private List<Board> generateAdd(Board board, boolean isWhite) {
        List<Board> moves = new ArrayList<>();
        char piece = isWhite ? 'W' : 'B';
        
        // Try placing piece on each empty position
        for (int location = 0; location < 21; location++) {
            if (board.getPosition().charAt(location) == 'x') {  // Empty position
                Board newBoard = board.placePiece(location, piece);
                if (newBoard != null) {
                    if (newBoard.closeMill(location, newBoard)) {
                        // Mill formed - must remove opponent piece
                        generateRemove(newBoard, moves, !isWhite);
                    } else {
                        // No mill - just add the board
                        moves.add(newBoard);
                    }
                }
            }
        }
        
        return moves;
    }

    private void generateRemove(Board board, List<Board> moveList, boolean removeWhite) {
        char opponent = removeWhite ? 'W' : 'B';
        String position = board.getPosition();
        boolean removedAny = false;
        
        // Try removing each opponent piece
        for (int location = 0; location < 21; location++) {
            if (position.charAt(location) == opponent) {
                if (!board.closeMill(location, board)) {  // Can only remove non-mill pieces
                    Board afterRemoval = board.removePiece(location);
                    if (afterRemoval != null) {
                        moveList.add(afterRemoval);
                        removedAny = true;
                    }
                }
            }
        }
        
        // If no pieces could be removed (all in mills), add original board
        if (!removedAny) {
            moveList.add(board);
        }
    }
 
    public static void main(String[] args) {
        System.out.println("=== Testing OpeningMoveGenerator ===");
        
        Board testBoard = new Board("xxxxxxxWxxxxxxBxxxxxx");
        OpeningMoveGenerator generator = new OpeningMoveGenerator();
        
        // Test White moves
        List<Board> whiteMoves = generator.generateMoves(testBoard, true);
        System.out.println("White can make " + whiteMoves.size() + " moves");
        System.out.println("First 3 White moves:");
        for (int i = 0; i < Math.min(3, whiteMoves.size()); i++) {
            System.out.println("  " + (i+1) + ": " + whiteMoves.get(i).getPosition());
        }
        
        // Test Black moves  
        List<Board> blackMoves = generator.generateMoves(testBoard, false);
        System.out.println("Black can make " + blackMoves.size() + " moves");
        System.out.println("First 3 Black moves:");
        for (int i = 0; i < Math.min(3, blackMoves.size()); i++) {
            System.out.println("  " + (i+1) + ": " + blackMoves.get(i).getPosition());
        }
        
        System.out.println("=== OpeningMoveGenerator tests completed! ===");
    }
}
package morris;

import java.util.*;
public class MidgameMoveGenerator extends MoveGenerator {
    public List<Board> generateMoves(Board board, boolean isWhite) {
        int playerPieces = isWhite ? board.countWhitePieces() : board.countBlackPieces();

        // If player has exactly 3 pieces, they can "hop" (fly to any empty position)
        if (playerPieces == 3) {
            return generateHopping(board, isWhite);
        } else {
            // Normal midgame: move to adjacent positions only
            return generateMove(board, isWhite);
        }
    }

    private List<Board> generateMove(Board board, boolean isWhite) {
        List<Board> moves = new ArrayList<>();
        char piece = isWhite ? 'W' : 'B';
        String position = board.getPosition();

        // For each piece of the current player
        for (int location = 0; location < 21; location++) {
            if (position.charAt(location) == piece) {
                // Get all neighboring positions
                List<Integer> neighbors = Board.getNeighbors(location);

                // Try moving to each empty neighbor
                for (int neighbor : neighbors) {
                    if (position.charAt(neighbor) == 'x') { // Empty neighbor
                        Board newBoard = board.movePiece(location, neighbor);
                        if (newBoard != null) {
                            if (newBoard.closeMill(neighbor, newBoard)) {
                                // Mill formed - remove opponent pieces
                                generateRemove(newBoard, moves, !isWhite);
                            } else {
                                // No mill - just add the move
                                moves.add(newBoard);
                            }
                        }
                    }
                }
            }
        }

        return moves;
    }

    private List<Board> generateHopping(Board board, boolean isWhite) {
        List<Board> moves = new ArrayList<>();
        char piece = isWhite ? 'W' : 'B';
        String position = board.getPosition();

        // For each piece of the current player (should be exactly 3)
        for (int from = 0; from < 21; from++) {
            if (position.charAt(from) == piece) {
                // Try moving to every empty position on the board
                for (int to = 0; to < 21; to++) {
                    if (position.charAt(to) == 'x') { // Empty position
                        Board newBoard = board.movePiece(from, to);
                        if (newBoard != null) {
                            if (newBoard.closeMill(to, newBoard)) {
                                // Mill formed - remove opponent pieces
                                generateRemove(newBoard, moves, !isWhite);
                            } else {
                                // No mill - just add the move
                                moves.add(newBoard);
                            }
                        }
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

        for (int location = 0; location < 21; location++) {
            if (position.charAt(location) == opponent) {
                if (!board.closeMill(location, board)) {
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
        System.out.println("=== Testing MidgameMoveGenerator ===");

        // Test normal midgame scenario (more than 3 pieces each)
        Board midgameBoard = new Board("xWWWxxxBBBxxxxxxxxxxx");
        MidgameMoveGenerator generator = new MidgameMoveGenerator();

        System.out.println("Midgame test board: " + midgameBoard.getPosition());
        System.out.println("White pieces: " + midgameBoard.countWhitePieces());
        System.out.println("Black pieces: " + midgameBoard.countBlackPieces());

        List<Board> whiteMoves = generator.generateMoves(midgameBoard, true);
        System.out.println("White midgame moves: " + whiteMoves.size());

        // Test endgame scenario (exactly 3 pieces)
        Board endgameBoard = new Board("xWxWxWxBBBxxxxxxxxxxx");
        System.out.println("\nEndgame test board: " + endgameBoard.getPosition());
        System.out.println("White pieces: " + endgameBoard.countWhitePieces());
        System.out.println("Black pieces: " + endgameBoard.countBlackPieces());

        List<Board> whiteEndgameMoves = generator.generateMoves(endgameBoard, true);
        System.out.println("White endgame (hopping) moves: " + whiteEndgameMoves.size());

        // Show a few example moves
        System.out.println("\nFirst 3 endgame moves:");
        for (int i = 0; i < Math.min(3, whiteEndgameMoves.size()); i++) {
            System.out.println("  " + (i + 1) + ": " + whiteEndgameMoves.get(i).getPosition());
        }

        System.out.println("=== MidgameMoveGenerator tests completed! ===");
    }
}
package morris;

import java.util.*;
public class Board {
    private final String position; // 21-character board state
    private static int positionsEvaluated = 0; // Counter for performance tracking

    public Board(String position) {
        if (position == null || position.length() != 21) {
            throw new IllegalArgumentException("Board position must be exactly 21 characters");
        }
        this.position = position;
    }

    // Basic getter methods
    public String getPosition() {
        return position;
    }

    public static int getPositionsEvaluated() {
        return positionsEvaluated;
    }

    public static void resetPositionsEvaluated() {
        positionsEvaluated = 0;
    }

    public int countWhitePieces() {
        return (int) position.chars().filter(ch -> ch == 'W').count();
    }

    public int countBlackPieces() {
        return (int) position.chars().filter(ch -> ch == 'B').count();
    }

    public static List<Integer> getNeighbors(int location) {
        switch (location) {
            case 0:
                return Arrays.asList(1, 2, 6); 
            case 1:
                return Arrays.asList(0, 3, 11); 
            case 2:
                return Arrays.asList(0, 3, 4, 7); 
            case 3:
                return Arrays.asList(1, 2, 5, 10); 
            case 4:
                return Arrays.asList(2, 5, 8); 
            case 5:
                return Arrays.asList(3, 4, 9); 
            case 6:
                return Arrays.asList(0, 7, 18);
            case 7:
                return Arrays.asList(2, 6, 8, 15); 
            case 8:
                return Arrays.asList(4, 7, 12); 
            case 9:
                return Arrays.asList(5, 10, 14); 
            case 10:
                return Arrays.asList(3, 9, 11, 17); 
            case 11:
                return Arrays.asList(1, 10, 20); 
            case 12:
                return Arrays.asList(8, 13, 15); 
            case 13:
                return Arrays.asList(12, 14, 16); 
            case 14:
                return Arrays.asList(9, 13, 17); 
            case 15:
                return Arrays.asList(7, 12, 16, 18); 
            case 16:
                return Arrays.asList(13, 15, 17, 19); 
            case 17:
                return Arrays.asList(10, 14, 16, 20); 
            case 18:
                return Arrays.asList(6, 15, 19); 
            case 19:
                return Arrays.asList(16, 18, 20); 
            case 20:
                return Arrays.asList(11, 17, 19); 
            default:
                return new ArrayList<>();
        }
    }

    public boolean closeMill(int location, Board board) {
        char piece = board.position.charAt(location);
        if (piece == 'x')
            return false; // Empty position can't close a mill

        String pos = board.position;

        // Check all possible mills for each position (brute force approach)
        switch (location) {
            case 0: // a0
                return (pos.charAt(2) == piece && pos.charAt(4) == piece) || 
                        (pos.charAt(6) == piece && pos.charAt(18) == piece);  

            case 1: // g0
                return (pos.charAt(3) == piece && pos.charAt(5) == piece) ||  
                        (pos.charAt(11) == piece && pos.charAt(20) == piece); 

            case 2: // b1
                return (pos.charAt(0) == piece && pos.charAt(4) == piece) || 
                        (pos.charAt(7) == piece && pos.charAt(15) == piece); 

            case 3: // f1
                return (pos.charAt(1) == piece && pos.charAt(5) == piece) ||  
                        (pos.charAt(10) == piece && pos.charAt(17) == piece);  

            case 4: // c2
                return (pos.charAt(0) == piece && pos.charAt(2) == piece) || 
                        (pos.charAt(8) == piece && pos.charAt(12) == piece); 

            case 5: // e2
                return (pos.charAt(1) == piece && pos.charAt(3) == piece) ||  
                        (pos.charAt(9) == piece && pos.charAt(14) == piece); 

            case 6: // a3
                return (pos.charAt(0) == piece && pos.charAt(18) == piece) || 
                        (pos.charAt(7) == piece && pos.charAt(8) == piece); 

            case 7: // b3
                return (pos.charAt(2) == piece && pos.charAt(15) == piece) || 
                        (pos.charAt(6) == piece && pos.charAt(8) == piece); 

            case 8: // c3
                return (pos.charAt(4) == piece && pos.charAt(12) == piece) || 
                        (pos.charAt(6) == piece && pos.charAt(7) == piece); 

            case 9: // e3
                return (pos.charAt(5) == piece && pos.charAt(14) == piece) || 
                        (pos.charAt(10) == piece && pos.charAt(11) == piece); 

            case 10: // f3
                return (pos.charAt(3) == piece && pos.charAt(17) == piece) || 
                        (pos.charAt(9) == piece && pos.charAt(11) == piece); 

            case 11: // g3
                return (pos.charAt(1) == piece && pos.charAt(20) == piece) || 
                        (pos.charAt(9) == piece && pos.charAt(10) == piece); 

            case 12: // c4
                return (pos.charAt(4) == piece && pos.charAt(8) == piece) || 
                        (pos.charAt(13) == piece && pos.charAt(14) == piece)||
                        (pos.charAt(15) == piece && pos.charAt(18) == piece); 

            case 13: // d4
                return (pos.charAt(12) == piece && pos.charAt(14) == piece) || 
                        (pos.charAt(16) == piece && pos.charAt(19) == piece); 

            case 14: // e4
                return (pos.charAt(5) == piece && pos.charAt(9) == piece) || 
                        (pos.charAt(12) == piece && pos.charAt(13) == piece)||
                        (pos.charAt(17) == piece && pos.charAt(20) == piece); 

            case 15: // b5
                return (pos.charAt(2) == piece && pos.charAt(7) == piece) || 
                        (pos.charAt(16) == piece && pos.charAt(17) == piece)||
                        (pos.charAt(12) == piece && pos.charAt(18) == piece); 

            case 16: // d5
                return (pos.charAt(13) == piece && pos.charAt(19) == piece) || 
                        (pos.charAt(15) == piece && pos.charAt(17) == piece); 

            case 17: // f5
                return (pos.charAt(3) == piece && pos.charAt(10) == piece) || 
                        (pos.charAt(15) == piece && pos.charAt(16) == piece)||
                        (pos.charAt(14) == piece && pos.charAt(20) == piece); 

            case 18: // a6
                return (pos.charAt(0) == piece && pos.charAt(6) == piece) || 
                        (pos.charAt(19) == piece && pos.charAt(20) == piece)||
                        (pos.charAt(12) == piece && pos.charAt(15) == piece); 

            case 19: // d6
                return (pos.charAt(13) == piece && pos.charAt(16) == piece) || 
                        (pos.charAt(18) == piece && pos.charAt(20) == piece); 

            case 20: // g6
                return (pos.charAt(1) == piece && pos.charAt(11) == piece) || 
                        (pos.charAt(18) == piece && pos.charAt(19) == piece)||
                        (pos.charAt(14) == piece && pos.charAt(17) == piece); 

            default:
                return false;
        }
    }

    public Board placePiece(int location, char piece) {
        if (location < 0 || location >= 21 || position.charAt(location) != 'x') {
            return null; // Invalid move
        }
        char[] newPos = position.toCharArray();
        newPos[location] = piece;
        return new Board(new String(newPos));
    }

    public Board movePiece(int from, int to) {
        if (from < 0 || from >= 21 || to < 0 || to >= 21 ||
                position.charAt(from) == 'x' || position.charAt(to) != 'x') {
            return null; // Invalid move
        }
        char[] newPos = position.toCharArray();
        char piece = newPos[from];
        newPos[from] = 'x';
        newPos[to] = piece;
        return new Board(new String(newPos));
    }

    public Board removePiece(int location) {
        if (location < 0 || location >= 21 || position.charAt(location) == 'x') {
            return null; // Invalid removal
        }
        char[] newPos = position.toCharArray();
        newPos[location] = 'x';
        return new Board(new String(newPos));
    }

    public Board flipBoard() {
        char[] flipped = new char[21];
        for (int i = 0; i < 21; i++) {
            char c = position.charAt(i);
            switch (c) {
                case 'W':
                    flipped[i] = 'B';
                    break;
                case 'B':
                    flipped[i] = 'W';
                    break;
                case 'x':
                    flipped[i] = 'x';
                    break;
                default:
                    flipped[i] = c;
                    break;
            }
        }
        return new Board(new String(flipped));
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Board Class ===");

        // Test basic board creation
        Board testBoard = new Board("xxxxxxxWxxxxxxBxxxxxx");
        System.out.println("Original board: " + testBoard.getPosition());
        System.out.println("White pieces: " + testBoard.countWhitePieces());
        System.out.println("Black pieces: " + testBoard.countBlackPieces());

        // Test board flipping
        Board flipped = testBoard.flipBoard();
        System.out.println("Flipped board:  " + flipped.getPosition());

        // Test piece placement
        Board withNewPiece = testBoard.placePiece(0, 'W');
        if (withNewPiece != null) {
            System.out.println("After placing W at position 0: " + withNewPiece.getPosition());
        }

        // Test neighbors
        List<Integer> neighbors = getNeighbors(0);
        System.out.println("Neighbors of position 0: " + neighbors);

        // Test static evaluations
        resetPositionsEvaluated();
        int openingEval = staticEstimationOpening(testBoard);
        System.out.println("Opening evaluation: " + openingEval);

        int midgameEval = staticEstimationMidgameEndgame(testBoard);
        System.out.println("Midgame evaluation: " + midgameEval);

        System.out.println("Positions evaluated: " + getPositionsEvaluated());

        // Test mill detection with a correct mill case
        Board millBoard = new Board("WxWxWxxxxxxxxxxxxxxxx");
        boolean millFormed = millBoard.closeMill(4, millBoard);
        System.out.println("Mill test board: " + millBoard.getPosition());
        System.out.println("Mill formed at position 4: " + millFormed);
        System.out.println("(Testing mill: positions 0,2,4 = W,W,W)");

        System.out.println("=== Board tests completed! ===");
    }

    // implementation of static evaluation functions
    public static int staticEstimationOpening(Board board) {
        positionsEvaluated++; // Increment counter for performance tracking
        return board.countWhitePieces() - board.countBlackPieces();
    }

    public static int staticEstimationMidgameEndgame(Board board) {
        positionsEvaluated++;

        int numWhitePieces = board.countWhitePieces();
        int numBlackPieces = board.countBlackPieces();

        // Check winning conditions first (as per specification)
        if (numBlackPieces <= 2)
            return 10000; // White wins
        if (numWhitePieces <= 2)
            return -10000; // Black wins

        // For now, return basic evaluation (we'll add mobility check later)
        return 1000 * (numWhitePieces - numBlackPieces);
    }
    
    //improved static evaluation function for Part IV 
    public static int staticEstimationImproved(Board board) {
        positionsEvaluated++;

        int whitePieces = board.countWhitePieces();
        int blackPieces = board.countBlackPieces();
        int evaluation = 0;

        // winning conditions
        if (blackPieces <= 2)
            return 50000; // White wins
        if (whitePieces <= 2)
            return -50000; // Black wins

        // check mobility (no moves = loss)
        MidgameMoveGenerator generator = new MidgameMoveGenerator();
        List<Board> blackMoves = generator.generateMoves(board, false);
        if (blackMoves.isEmpty())
            return 50000; // Black has no moves

        List<Board> whiteMoves = generator.generateMoves(board, true);
        if (whiteMoves.isEmpty())
            return -50000; // White has no moves

        // material advantage
        evaluation += (whitePieces - blackPieces) * 1000;

        // mill advantage
        int whiteMills = countMills(board, 'W');
        int blackMills = countMills(board, 'B');
        evaluation += (whiteMills - blackMills) * 1200;

        // mill oppurtunities
        int whiteMillOpportunities = countMillOpportunities(board, 'W');
        int blackMillOpportunities = countMillOpportunities(board, 'B');
        evaluation += (whiteMillOpportunities - blackMillOpportunities) * 300;

        // number of possible moves
        int mobilityDifference = whiteMoves.size() - blackMoves.size();
        evaluation += mobilityDifference * 150;

        // centre control
        int whiteCenter = countCenterControl(board, 'W');
        int blackCenter = countCenterControl(board, 'B');
        evaluation += (whiteCenter - blackCenter) * 200;

        // blocking opponent mills
        int whiteBlocking = countBlockingPieces(board, 'W');
        int blackBlocking = countBlockingPieces(board, 'B');
        evaluation += (whiteBlocking - blackBlocking) * 250;

        // jumping ability
        if (whitePieces == 3 && blackPieces > 3)
            evaluation += 500;
        if (blackPieces == 3 && whitePieces > 3)
            evaluation -= 500;

        return evaluation;
    }

    private static int countMills(Board board, char player) {
        int mills = 0;
        String pos = board.getPosition();

        // All possible mill patterns on the Morris board
        int[][] millPatterns = {
                // Horizontal mills
                { 0, 2, 4 }, 
                { 1, 3, 5 },  
                { 6, 7, 8 }, 
                { 9, 10, 11 }, 
                { 12, 13, 14 }, 
                { 15, 16, 17 }, 
                { 18, 19, 20 }, 

                // Vertical mills
                { 0, 6, 18 }, 
                { 2, 7, 15 }, 
                { 4, 8, 12 }, 
                { 13, 16, 19 },
                { 5, 9, 14 }, 
                { 3, 10, 17 }, 
                { 1, 11, 20 } 
        };

        for (int[] mill : millPatterns) {
            if (pos.charAt(mill[0]) == player &&
                    pos.charAt(mill[1]) == player &&
                    pos.charAt(mill[2]) == player) {
                mills++;
            }
        }

        return mills;
    }

    private static int countMillOpportunities(Board board, char player) {
        int opportunities = 0;
        String pos = board.getPosition();

        int[][] millPatterns = {
                { 0, 2, 4 }, { 1, 3, 5 }, { 6, 7, 8 }, { 9, 10, 11 }, { 12, 13, 14 },
                { 15, 16, 17 }, { 18, 19, 20 }, { 0, 6, 18 }, { 2, 7, 15 }, { 4, 8, 12 },
                { 13, 16, 19 }, { 5, 9, 14 }, { 3, 10, 17 }, { 1, 11, 20 }
        };

        for (int[] mill : millPatterns) {
            int playerCount = 0;
            int emptyCount = 0;

            for (int position : mill) {
                if (pos.charAt(position) == player)
                    playerCount++;
                else if (pos.charAt(position) == 'x')
                    emptyCount++;
            }

            // Opportunity: 2 own pieces + 1 empty space
            if (playerCount == 2 && emptyCount == 1) {
                opportunities++;
            }
        }

        return opportunities;
    }

    private static int countCenterControl(Board board, char player) {
        String pos = board.getPosition();
        int centerCount = 0;

        // Strategic center positions 
        int[] centerPositions = { 7, 8, 9, 10, 13, 16 }; // b3, c3, e3, f3, d4, d5

        for (int position : centerPositions) {
            if (pos.charAt(position) == player) {
                centerCount++;
            }
        }

        return centerCount;
    }

    private static int countBlockingPieces(Board board, char player) {
        String pos = board.getPosition();
        char opponent = (player == 'W') ? 'B' : 'W';
        int blockingCount = 0;

        int[][] millPatterns = {
                { 0, 2, 4 }, { 1, 3, 5 }, { 6, 7, 8 }, { 9, 10, 11 }, { 12, 13, 14 },
                { 15, 16, 17 }, { 18, 19, 20 }, { 0, 6, 18 }, { 2, 7, 15 }, { 4, 8, 12 },
                { 13, 16, 19 }, { 5, 9, 14 }, { 3, 10, 17 }, { 1, 11, 20 }
        };

        for (int[] mill : millPatterns) {
            int playerCount = 0;
            int opponentCount = 0;

            for (int position : mill) {
                if (pos.charAt(position) == player)
                    playerCount++;
                else if (pos.charAt(position) == opponent)
                    opponentCount++;
            }

            // Blocking: opponent has 2 pieces, we have 1
            if (opponentCount == 2 && playerCount == 1) {
                blockingCount++;
            }
        }

        return blockingCount;
    }

}
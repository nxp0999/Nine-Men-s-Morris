package morris;

import java.nio.file.*;
import java.io.IOException;
public class MiniMaxGameBlack {
    public static void main(String[] args) {
        // Check command line arguments
        if (args.length != 3) {
            System.out.println("Usage: java morris.MiniMaxGameBlack <input_file> <output_file> <depth>");
            System.exit(1);
        }
        
        try {
            // Parse command line arguments
            String inputFile = args[0];
            String outputFile = args[1];
            int depth = Integer.parseInt(args[2]);
            
            // Validate depth
            if (depth < 1) {
                System.err.println("Error: Depth must be a positive integer");
                System.exit(1);
            }
            
            // Read initial board position from file
            String boardPosition = readBoardFromFile(inputFile);
            Board initialBoard = new Board(boardPosition);
            
            // Reset evaluation counter for accurate statistics
            Board.resetPositionsEvaluated();
            
            // BOARD FLIPPING STRATEGY for Black player
            // 1. Flip board (W<->B)
            Board flippedBoard = initialBoard.flipBoard();
            
            // 2. Use White algorithm on flipped board
            MidgameMoveGenerator generator = new MidgameMoveGenerator();
            MinimaxAlgorithm minimax = new MinimaxAlgorithm(generator, false); // false = midgame phase
            MinimaxAlgorithm.EvaluationResult result = minimax.minimax(flippedBoard, depth, true);
            
            // 3. Flip result back to original perspective
            Board finalBoard = result.getBestBoard().flipBoard();
            
            // Check if a valid move was found
            if (finalBoard == null) {
                System.err.println("Error: No valid moves found");
                System.exit(1);
            }
            
            // 4. Evaluation from Black's perspective (negate)
            int blackEvaluation = -result.getEvaluation();
            
            // Output results (use finalBoard and blackEvaluation)
            System.out.println("Board Position: " + finalBoard.getPosition());
            System.out.println("Positions evaluated by static estimation: " + Board.getPositionsEvaluated() + ".");
            System.out.println("MINIMAX estimate: " + blackEvaluation + ".");
            
            // Write result to output file
            writeBoardToFile(outputFile, finalBoard.getPosition());
            
        } catch (NumberFormatException e) {
            System.err.println("Error: Depth must be a valid integer");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading/writing files: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
 
    private static String readBoardFromFile(String filename) throws IOException {
        try {
            String content = Files.readString(Paths.get(filename));
            return content.trim(); // Remove any whitespace/newlines
        } catch (IOException e) {
            throw new IOException("Cannot read file: " + filename + " (" + e.getMessage() + ")");
        }
    }

    private static void writeBoardToFile(String filename, String boardPosition) throws IOException {
        try {
            Files.writeString(Paths.get(filename), boardPosition);
        } catch (IOException e) {
            throw new IOException("Cannot write file: " + filename + " (" + e.getMessage() + ")");
        }
    }
}
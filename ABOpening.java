package morris;

import java.nio.file.*;
import java.io.IOException;


public class ABOpening {
    public static void main(String[] args) {
        // Check command line arguments
        if (args.length != 3) {
            System.out.println("Usage: java morris.ABOpening <input_file> <output_file> <depth>");
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
            
            // Setup Alpha-Beta algorithm for opening phase
            OpeningMoveGenerator generator = new OpeningMoveGenerator();
            AlphaBetaAlgorithm alphaBeta = new AlphaBetaAlgorithm(generator, true); // true = opening phase
            
            // Find best move for White (maximizing player)
            AlphaBetaAlgorithm.EvaluationResult result = alphaBeta.search(initialBoard, depth, true);
            
            // Check if a valid move was found
            if (result.getBestBoard() == null) {
                System.err.println("Error: No valid moves found");
                System.exit(1);
            }
            
            // Output results in exact format required by assignment
            System.out.println("Board Position: " + result.getBestBoard().getPosition());
            System.out.println("Positions evaluated by static estimation: " + Board.getPositionsEvaluated() + ".");
            System.out.println("MINIMAX estimate: " + result.getEvaluation() + ".");
            
            // Write best board position to output file
            writeBoardToFile(outputFile, result.getBestBoard().getPosition());
            
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
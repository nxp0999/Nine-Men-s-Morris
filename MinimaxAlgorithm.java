package morris;

import java.util.*;
public class MinimaxAlgorithm {
    private final MoveGenerator moveGenerator;
    private final boolean isOpeningPhase;
    private final boolean useImprovedEvaluation;

    public static class EvaluationResult {
        private final Board bestBoard;
        private final int evaluation;
        
        public EvaluationResult(Board board, int eval) {
            this.bestBoard = board;
            this.evaluation = eval;
        }
        
        public Board getBestBoard() { return bestBoard; }
        public int getEvaluation() { return evaluation; }
    }
    
    // standard evaluation
    public MinimaxAlgorithm(MoveGenerator generator, boolean isOpening) {
        this.moveGenerator = generator;
        this.isOpeningPhase = isOpening;
        this.useImprovedEvaluation = false;
    }

    // improved standard evaluation (Part IV)

    public MinimaxAlgorithm(MoveGenerator generator, boolean isOpening, boolean improved) {
        this.moveGenerator = generator;
        this.isOpeningPhase = isOpening;
        this.useImprovedEvaluation = improved;
    }

    public EvaluationResult minimax(Board board, int depth, boolean isMaximizing) {
        // Base case: reached maximum depth or leaf node
        if (depth == 0) {
            int evaluation = evaluateBoard(board);
            return new EvaluationResult(board, evaluation);
        }
        
        // Generate all possible moves for current player
        List<Board> possibleMoves = moveGenerator.generateMoves(board, isMaximizing);
        
        // If no moves available, evaluate current position (game might be over)
        if (possibleMoves.isEmpty()) {
            int evaluation = evaluateBoard(board);
            return new EvaluationResult(board, evaluation);
        }
        
        // Choose between MAX and MIN logic based on current player
        if (isMaximizing) {
            return maxValue(possibleMoves, depth);
        } else {
            return minValue(possibleMoves, depth);
        }
    }
    
    private EvaluationResult maxValue(List<Board> moves, int depth) {
        int bestValue = Integer.MIN_VALUE;
        Board bestBoard = null;
        
        for (Board move : moves) {
            // Recursively evaluate this move (opponent's turn, so MIN)
            EvaluationResult result = minimax(move, depth - 1, false);
            
            if (result.getEvaluation() > bestValue) {
                bestValue = result.getEvaluation();
                bestBoard = move;
            }
        }
        
        return new EvaluationResult(bestBoard, bestValue);
    }
 
    private EvaluationResult minValue(List<Board> moves, int depth) {
        int bestValue = Integer.MAX_VALUE;
        Board bestBoard = null;
        
        for (Board move : moves) {
            // Recursively evaluate this move (opponent's turn, so MAX)
            EvaluationResult result = minimax(move, depth - 1, true);
            
            if (result.getEvaluation() < bestValue) {
                bestValue = result.getEvaluation();
                bestBoard = move;
            }
        }
        
        return new EvaluationResult(bestBoard, bestValue);
    }
 
    private int evaluateBoard(Board board) {
        if (useImprovedEvaluation) {
            return Board.staticEstimationImproved(board);
        } else if (isOpeningPhase) {
            return Board.staticEstimationOpening(board);
        } else {
            return Board.staticEstimationMidgameEndgame(board);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Testing MINIMAX Algorithm ===");
        
        // Test with opening phase
        Board testBoard = new Board("xxxxxxxWxxxxxxBxxxxxx");
        System.out.println("Test board: " + testBoard.getPosition());
        
        OpeningMoveGenerator generator = new OpeningMoveGenerator();
        MinimaxAlgorithm minimax = new MinimaxAlgorithm(generator, true);
        
        // Reset counter for accurate statistics
        Board.resetPositionsEvaluated();
        
        // Test with depth 2 (manageable for testing)
        System.out.println("Running MINIMAX with depth 2...");
        EvaluationResult result = minimax.minimax(testBoard, 2, true);
        
        System.out.println("Best move found: " + result.getBestBoard().getPosition());
        System.out.println("Evaluation score: " + result.getEvaluation());
        System.out.println("Positions evaluated: " + Board.getPositionsEvaluated());
        
        // Test with depth 1 for comparison
        System.out.println("\nRunning MINIMAX with depth 1 for comparison...");
        Board.resetPositionsEvaluated();
        EvaluationResult result1 = minimax.minimax(testBoard, 1, true);
        
        System.out.println("Depth 1 - Best move: " + result1.getBestBoard().getPosition());
        System.out.println("Depth 1 - Evaluation: " + result1.getEvaluation());
        System.out.println("Depth 1 - Positions evaluated: " + Board.getPositionsEvaluated());
        
        System.out.println("=== MINIMAX Algorithm tests completed! ===");
    }
}
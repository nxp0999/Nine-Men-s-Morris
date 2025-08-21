package morris;

import java.util.*;

public class AlphaBetaAlgorithm {
    private final MoveGenerator moveGenerator;
    private final boolean isOpeningPhase;
    private final boolean useImprovedEvaluation;

    // Result class
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

    public AlphaBetaAlgorithm(MoveGenerator generator, boolean isOpening) {
        this.moveGenerator = generator;
        this.isOpeningPhase = isOpening;
        this.useImprovedEvaluation = false;
    }

    //Part 4 (Standard Evaluation)
    public AlphaBetaAlgorithm(MoveGenerator generator, boolean isOpening, boolean improved) {
        this.moveGenerator = generator;
        this.isOpeningPhase = isOpening;
        this.useImprovedEvaluation = improved;
    }
    
    public EvaluationResult search(Board board, int depth, boolean isMaximizing) {
        return alphaBeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, isMaximizing);
    }
   
    public EvaluationResult alphaBeta(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        // Base case: reached maximum depth or leaf node
        if (depth == 0) {
            int evaluation = evaluateBoard(board);
            return new EvaluationResult(board, evaluation);
        }
        
        // Generate all possible moves for current player
        List<Board> possibleMoves = moveGenerator.generateMoves(board, isMaximizing);
        
        // If no moves available, evaluate current position
        if (possibleMoves.isEmpty()) {
            int evaluation = evaluateBoard(board);
            return new EvaluationResult(board, evaluation);
        }
        
        // Choose between MAX and MIN logic with pruning
        if (isMaximizing) {
            return maxValueAB(possibleMoves, depth, alpha, beta);
        } else {
            return minValueAB(possibleMoves, depth, alpha, beta);
        }
    }

    private EvaluationResult maxValueAB(List<Board> moves, int depth, int alpha, int beta) {
        int bestValue = Integer.MIN_VALUE;
        Board bestBoard = null;
        
        for (Board move : moves) {
            EvaluationResult result = alphaBeta(move, depth - 1, alpha, beta, false);
            
            if (result.getEvaluation() > bestValue) {
                bestValue = result.getEvaluation();
                bestBoard = move;
            }
            
            // Update alpha (best value MAX can guarantee)
            alpha = Math.max(alpha, bestValue);
            
            // Beta cut-off: if current value >= beta, MIN player won't choose this path
            if (bestValue >= beta) {
                break; // Prune remaining branches
            }
        }
        
        return new EvaluationResult(bestBoard, bestValue);
    }
    
    private EvaluationResult minValueAB(List<Board> moves, int depth, int alpha, int beta) {
        int bestValue = Integer.MAX_VALUE;
        Board bestBoard = null;
        
        for (Board move : moves) {
            EvaluationResult result = alphaBeta(move, depth - 1, alpha, beta, true);
            
            if (result.getEvaluation() < bestValue) {
                bestValue = result.getEvaluation();
                bestBoard = move;
            }
            
            // Update beta (best value MIN can guarantee)
            beta = Math.min(beta, bestValue);
            
            // Alpha cut-off: if current value <= alpha, MAX player won't choose this path
            if (bestValue <= alpha) {
                break; // Prune remaining branches
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
        System.out.println("=== Testing Alpha-Beta vs MINIMAX ===");
        
        Board testBoard = new Board("xxxxxxxWxxxxxxBxxxxxx");
        System.out.println("Test board: " + testBoard.getPosition());
        
        OpeningMoveGenerator generator = new OpeningMoveGenerator();
        
        // Test MINIMAX first
        System.out.println("\n--- MINIMAX Results ---");
        Board.resetPositionsEvaluated();
        MinimaxAlgorithm minimax = new MinimaxAlgorithm(generator, true);
        MinimaxAlgorithm.EvaluationResult minimaxResult = minimax.minimax(testBoard, 3, true);
        int minimaxEvaluations = Board.getPositionsEvaluated();
        
        System.out.println("MINIMAX - Best move: " + minimaxResult.getBestBoard().getPosition());
        System.out.println("MINIMAX - Evaluation: " + minimaxResult.getEvaluation());
        System.out.println("MINIMAX - Positions evaluated: " + minimaxEvaluations);
        
        // Test Alpha-Beta
        System.out.println("\n--- Alpha-Beta Results ---");
        Board.resetPositionsEvaluated();
        AlphaBetaAlgorithm alphaBeta = new AlphaBetaAlgorithm(generator, true);
        EvaluationResult abResult = alphaBeta.search(testBoard, 3, true);
        int abEvaluations = Board.getPositionsEvaluated();
        
        System.out.println("Alpha-Beta - Best move: " + abResult.getBestBoard().getPosition());
        System.out.println("Alpha-Beta - Evaluation: " + abResult.getEvaluation());
        System.out.println("Alpha-Beta - Positions evaluated: " + abEvaluations);
        
        // Compare efficiency
        System.out.println("\n--- Efficiency Comparison ---");
        boolean sameResult = minimaxResult.getBestBoard().getPosition().equals(abResult.getBestBoard().getPosition());
        System.out.println("Same best move: " + sameResult);
        System.out.println("Same evaluation: " + (minimaxResult.getEvaluation() == abResult.getEvaluation()));
        
        int saved = minimaxEvaluations - abEvaluations;
        double percentage = (double) saved / minimaxEvaluations * 100;
        System.out.println("Positions saved: " + saved + " (" + String.format("%.1f", percentage) + "% reduction)");
        
        System.out.println("=== Alpha-Beta tests completed! ===");
    }
}
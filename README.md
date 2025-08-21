Complete implementation of Morris-Variant-E artificial intelligence game player. Features MINIMAX and Alpha-Beta algorithms with strategic evaluation functions, mill detection, and tournament-optimized performance.

## Features

- All 8 required programs implemented and functional
- MINIMAX algorithm with complete game tree search
- Alpha-Beta pruning achieving 89% efficiency improvement
- Advanced static evaluation with strategic factors
- Board flipping technique for Black player programs
- Complete mill detection and piece removal logic
- Flying phase support for endgame scenarios
- Tournament-compliant timing under 20-second limits

## Compilation

```
javac src/morris/*.java
```

## Usage

All programs follow the same command format:
```
java -cp src morris.[ProgramName] [input_file] [output_file] [depth]
```

### Examples
```
java -cp src morris.ABOpening board1.txt output1.txt 6
java -cp src morris.MiniMaxGame board2.txt output2.txt 7
java -cp src morris.MiniMaxOpeningBlack board3.txt output3.txt 5
```

## Programs Included

### Part I: MINIMAX Implementation
- MiniMaxOpening.java - Opening phase with MINIMAX algorithm
- MiniMaxGame.java - Midgame/endgame phase with MINIMAX algorithm

### Part II: Alpha-Beta Implementation  
- ABOpening.java - Opening phase with Alpha-Beta pruning
- ABGame.java - Midgame/endgame phase with Alpha-Beta pruning

### Part III: Black Player Programs
- MiniMaxOpeningBlack.java - Black opening moves using board flipping
- MiniMaxGameBlack.java - Black midgame moves using board flipping

### Part IV: Improved Evaluation
- MiniMaxOpeningImproved.java - Enhanced strategic evaluation for opening
- MiniMaxGameImproved.java - Enhanced strategic evaluation for midgame

## Performance Recommendations

### Optimal Depth Settings
- Opening Phase: Depth 6-7 (fast piece placement)
- Midgame Phase: Depth 7-8 (optimal strategic analysis)
- Endgame Flying: Depth 4 (prevents timeout due to combinatorial explosion)

### Tournament Strategy
Use Alpha-Beta programs for maximum efficiency while maintaining identical results to MINIMAX implementations.

## Implementation Details

### Board Representation
21-character string representing Morris board positions 0-20 with 'W' for White pieces, 'B' for Black pieces, and 'x' for empty positions.

### Move Generation
- Opening: Place pieces on empty positions with mill detection
- Midgame: Move pieces to adjacent empty positions
- Endgame: Flying mode when player has 3 or fewer pieces

### Evaluation Functions
- Basic: Simple piece counting difference
- Improved: Multi-factor analysis including mill opportunities, mobility, center control, and blocking potential

### Mill Detection
Complete mill pattern recognition with automatic opponent piece removal following Morris rules. Players cannot remove pieces that are part of opponent mills.

### Board Flipping
Black player programs use board flipping technique to eliminate code duplication. Board state is flipped to White perspective, algorithm executed, then result flipped back.

## Algorithm Efficiency

Alpha-Beta pruning provides significant performance improvement over basic MINIMAX while guaranteeing identical results. Testing shows approximately 89% reduction in position evaluations.

## Files Structure

```
src/morris/
├── Board.java                    # Core board representation and operations
├── MoveGenerator.java            # Abstract base for move generation
├── OpeningMoveGenerator.java     # Opening phase move generation
├── MidgameMoveGenerator.java     # Midgame and endgame move generation
├── MinimaxAlgorithm.java         # MINIMAX search implementation
├── AlphaBetaAlgorithm.java       # Alpha-Beta search implementation
├── [8 main program files]        # Required submission programs
└── [test data and examples]      # Sample board positions
```

## Testing

The implementation has been extensively tested with various board positions covering opening, midgame, and endgame scenarios. All programs produce correct output format and handle edge cases properly.

The following contains all the tested outputs and the filenames of each output

SET 1

# Part I: MINIMAX   

nxp230016 % java -cp src morris.MiniMaxOpening test-data/board1.txt output/out1.txt 3

Board Position: WxxxxxxWxxxxxxBxxxxxx
Positions evaluated by static estimation: 5882.
MINIMAX estimate: 1.         


nxp230016 % java -cp src morris.MiniMaxGame test-data/board2.txt output/out2.txt 2

Board Position: xxxBWxWWWWBBBBxxxxxxx
Positions evaluated by static estimation: 56.
MINIMAX estimate: 0.


# Part II: ALPHA-BETA  

nxp230016 % java -cp src morris.ABOpening test-data/board1.txt output/out3.txt 3

Board Position: WxxxxxxWxxxxxxBxxxxxx
Positions evaluated by static estimation: 606.
MINIMAX estimate: 1.


nxp230016 % java -cp src morris.ABGame test-data/board2.txt output/out4.txt 2

Board Position: xxxBWxWWWWBBBBxxxxxxx
Positions evaluated by static estimation: 15.
MINIMAX estimate: 0.


# Part III: BLACK PLAYER  

nxp230016 % java -cp src morris.MiniMaxOpeningBlack test-data/board1.txt output/out5.txt 3

Board Position: BxxxxxxWxxxxxxBxxxxxx
Positions evaluated by static estimation: 5916.
MINIMAX estimate: -1.


nxp230016 % java -cp src morris.MiniMaxGameBlack test-data/board2.txt output/out6.txt 2

Board Position: xBxxxWWWWWBBBBxxxxxxx
Positions evaluated by static estimation: 57.
MINIMAX estimate: 0.


# Part IV: IMPROVED EVALUATION

nxp230016 % java -cp src morris.MiniMaxOpeningImproved test-data/board1.txt output/out7.txt 3

Board Position: WxxxxxxWxxxxxxBxxxxxx
Positions evaluated by static estimation: 5882.
MINIMAX estimate: 50000.



nxp230016 % java -cp src morris.MiniMaxGameImproved test-data/board2.txt output/out8.txt 2

Board Position: xxxBxWWWWxBBBBWxxxxxx
Positions evaluated by static estimation: 56.
MINIMAX estimate: 450.



SET 2 

# Alpha-Beta Efficiency Examples 

nxp230016 % java -cp src morris.MiniMaxOpening test-data/board1.txt output/output1a.txt 4

Board Position: WxxxxxxWxxxxxxBxxxxxx
Positions evaluated by static estimation: 97464.
MINIMAX estimate: 0.


nxp230016 % java -cp src morris.ABOpening test-data/board1.txt output/output1b.txt 4

Board Position: WxxxxxxWxxxxxxBxxxxxx
Positions evaluated by static estimation: 2044.
MINIMAX estimate: 0.


nxp230016 % java -cp src morris.MiniMaxGame test-data/board4.txt output/output2a.txt 5 

Board Position: WxxxxxxWWxWWxBBBBxxxx
Positions evaluated by static estimation: 152779.
MINIMAX estimate: 0.


nxp230016 % java -cp src morris.ABGame test-data/board4.txt output/output2b.txt 5

Board Position: WxxxxxxWWxWWxBBBBxxxx
Positions evaluated by static estimation: 3953.
MINIMAX estimate: 0.


# Improved Evaluation Examples 

nxp230016 % java -cp src morris.MiniMaxOpening test-data/board1.txt output/output3a.txt 4

Board Position: WxxxxxxWxxxxxxBxxxxxx
Positions evaluated by static estimation: 97464.
MINIMAX estimate: 0.


nxp230016 % java -cp src morris.MiniMaxOpeningImproved test-data/board1.txt output/output3b.txt 4

Board Position: xxxxxxxWWxxxxxBxxxxxx
Positions evaluated by static estimation: 97464.
MINIMAX estimate: -100.


nxp230016 % java -cp src morris.MiniMaxGame test-data/board3.txt output/output4a.txt 5

Board Position: xxxxWxxxxWxWWxBBBxxxx
Positions evaluated by static estimation: 4416864.
MINIMAX estimate: 0.


nxp230016 % java -cp src morris.MiniMaxGameImproved test-data/board4.txt output/output4b.txt 5

Board Position: xxxWxxWWWxxWxBBBBxxxx
Positions evaluated by static estimation: 152779.
MINIMAX estimate: 200.



## Tournament Compliance

All programs meet tournament requirements including 20-second time limits, correct input/output formatting, and proper error handling. The adaptive depth strategy ensures reliable performance under competition conditions.


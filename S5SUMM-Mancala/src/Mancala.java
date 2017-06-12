import utility.MockIO;
public class Mancala {
    private boolean isRunning = true;
    private Board board;
    private int currentPlayer = 1;
    private MockIO io;


    public static void main(String[] args) {
		new Mancala().play(new MockIO());
	}
	
	
	public void play(MockIO io) {
        this.io = io;
        int blocksPerPlayer = 6;
        int stonesPerBlock = 4;
        board = new Board(io, blocksPerPlayer, stonesPerBlock);
        
        while(isRunning){
            board.printBoard();
            board.setPlayer(currentPlayer);
            io.println(String.format("Player %d's turn - Specify block number or 'q' to quit: ", currentPlayer));
            /* Take user input*/
            int selectedBlock = io.readInteger("", 1, 6, -1, "q");
            if (selectedBlock == -1){
                isRunning = false;
                io.println("Game over");
                board.printBoard();
                break;
            }
            /* Get Stones from each pit and distribute*/
            int Stones = board.getStonesFromBlock(currentPlayer, selectedBlock);
            if (Stones == 0){
            	io.println("Block is empty. Move again.");
                continue;
            }
            GameMove move = null;
            for (int i = 0; i < Stones; i++){
                move = board.insertStoneIntoNextPit(Stones-i);
            }
	     /* Final stone placement dictates how the implementation proceeds 
            * Access "move" inside the loop to do something after every placed Stone*/
            switch (move){
                case GO_TO_NEXT:
                    /* Turn finished on a Block in normal conditions*/
                    switchPlayer();
                    break;
                case EXTRA_TURN:
                    /*Turn ended in player's own store*/
                    break;
                case STEAL_STONES:
                    /*Turn ended in an empty Block belonging to the player*/
                    board.moveTwoBlockContentsToStore();
                    switchPlayer();
                    break;

        
            }
            isRunning = isThereAnotherTurn();

        }
    }
	private void switchPlayer(){
        if (currentPlayer == 1){
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
    }

	/* Evaluate the end conditions for a given game*/
	private boolean isThereAnotherTurn() {
        /* Checks if either player has empty blocks*/
        int p1sum = board.countStonesInPlayerBlocks(1);
        int p2sum = board.countStonesInPlayerBlocks(2);

        if (p1sum == 0 || p2sum == 0) {
            io.println("Game over");
            board.printBoard();
            /* Get stone totals to display to user*/
            p1sum += board.countStonesInPlayerStore(1);
            p2sum += board.countStonesInPlayerStore(2);

            io.println("\tplayer 1:"+p1sum);
            io.println("\tplayer 2:"+p2sum);
            if (p1sum > p2sum) {
                io.println("Player 1 wins!");
            } else if (p2sum > p1sum){
                io.println("Player 2 wins!");
            } else {
                io.println("A tie!");
            }
            return false; // Game ends
        }
        return true; // New turn begins
    }

}
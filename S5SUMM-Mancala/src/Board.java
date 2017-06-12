import utility.MockIO;
/**
 * Manages all access between game function/logic and the function of each pit. 
 * Prints board for user to select from 
 */
class Board {
	private int p1Block1Index;
    private int p2Block1Index;
    private int p1StoreIndex;
    private int p2StoreIndex;
    private int currentPit;
    private int currentPlayer;
    private Pits boardArray[];
    private final int BlocksPerPlayer;
    private final int stonesPerBlock;
    private final int startingStonesInStore = 0;
    private final MockIO io;

    /* Constructor for the board */
    public Board(MockIO io, int blocksPerPlayer, int stonesPerHouse) {
        this.io = io;
        this.BlocksPerPlayer = blocksPerPlayer;
        this.stonesPerBlock = stonesPerHouse;
        createBoard();
    }

   
    /**
     * Construct the board set up for 2 players with a set of blocks containing pits that each have stones.
     */
    private void createBoard(){
        int boardSize = 2*BlocksPerPlayer + 2;  /* One store per player*/
        int halfBoard = boardSize / 2;
        p1Block1Index = 0;
        p2Block1Index = halfBoard;
        p1StoreIndex = halfBoard -1;
        p2StoreIndex = 2* halfBoard -1;
        boardArray = new Pits[boardSize];
        /* Format is: player 1 blocks, player 1 store, player 2 blocks, player 2 store*/
        for (int player = 1; player<=2;player++){
            for (int i= 0; i < BlocksPerPlayer; i++){
                boardArray[i+ halfBoard *(player-1)] = new Block(player, stonesPerBlock);
            }
            boardArray[(halfBoard *player)-1] = new StoneStore(player, startingStonesInStore);
        }
    }

    
    /**
     * Print board for user to see using strings and specific design 
     */
    public void printBoard(){
        String borderCenterSection = "";
        for (int i = 0; i < BlocksPerPlayer; i++){   
            borderCenterSection += "---------";
        }
        String borderRow = String.format("-------" + borderCenterSection + "------");
        String centreRow = String.format("|    |" + borderCenterSection.subSequence(0, borderCenterSection.length()-1) + "|    |"); 

        /* Top row is player 2*/
        String p2Row = "| P2 |";
        for (int i = 0; i < BlocksPerPlayer; i++){
            p2Row += String.format(" %d(%2d ) |",BlocksPerPlayer-i,boardArray[p2StoreIndex - 1 - i].getStoneCount());
        }
        p2Row += String.format(" %2d |", boardArray[p1StoreIndex].getStoneCount());

        /* Bottom row is player1*/
        String p1Row = String.format("| %2d |", boardArray[p2StoreIndex].getStoneCount());
        for (int i = 0; i < BlocksPerPlayer; i++){
            p1Row += String.format(" %d(%2d ) |",i+1, boardArray[p1Block1Index + i].getStoneCount());
        }
        p1Row += " P1 |";

        io.println(borderRow);
        io.println(p2Row);
        io.println(centreRow);
        io.println(p1Row);
        io.println(borderRow);

    }
    public void setPlayer(int player){
        currentPlayer = player;
    }
    /**
     * Takes in input values
     * BlockNumber is the value the user selects to move
     * @param player current player
     * @param BlockNumber selected by user
     * @return stone number
     */
    public int getStonesFromBlock(int player, int BlockNumber){
        currentPit = getArrayId(player, BlockNumber);
        return boardArray[currentPit].stealStones();
    }

    /**
     * Sets pit contents to 0 and returns the former value when pit is selected
     * Only for private use as it uses array id as input rather than game relevant values
     * @param id
     * @return
     */
    private int getStones(int id){
        return boardArray[id].stealStones();
    }

    /**
     * Converts user choice to array id
     * @param player
     * @param BlockNumber
     * @return
     */
    private int getArrayId(int player, int BlockNumber) {
        if (player == 1){
            return p1Block1Index + BlockNumber -1; /* -1 to account for different 0/1 counting*/
        } else {
            return p2Block1Index + BlockNumber -1;
        }
    }

    /**
     * Skips to next accessible container and attempts to add a single stone to it
     * @param stonesRemaining
     * @return
     */
    public GameMove insertStoneIntoNextPit(int stonesRemaining){
        getNextPit(); // Circular increment of array
        GameMove result =  boardArray[currentPit].addStone(currentPlayer, stonesRemaining);
        /* Case for landing in opponent's store*/
        if (result == null){
            /* Skip ahead one as it did not accept the stone*/
            getNextPit();
            result =  boardArray[currentPit].addStone(currentPlayer, stonesRemaining);
        }
        return result;
    }

    /* Seize the contents of a Block and its opposite block*/
    public void moveTwoBlockContentsToStore(){

        int playerBlockStones = getStones(currentPit);
        int opposingBlock = boardArray.length - currentPit -2; /* Ugly but works */

        int opponentBlockStones = getStones(opposingBlock);
        int total = opponentBlockStones + playerBlockStones;
        /* Put them in the correct store*/
        if (currentPlayer == 1){
            boardArray[p1StoreIndex].insertLiberatedStones(total);
        } else {
            boardArray[p2StoreIndex].insertLiberatedStones(total);
        }
    }

    /**
     * For checking if a game has ended
     * Will iterate over all of a player's Blocks and return the total number of seeds in residence
     * @param player
     * @return
     */
    public int countStonesInPlayerBlocks(int player){
        int firstBlock;
        int sum = 0;

        if (player == 1){
            firstBlock = p1Block1Index;
        } else {
            firstBlock = p2Block1Index;
        }

        for (int i = 0; i < BlocksPerPlayer; i++){
            sum += boardArray[firstBlock+i].numberOfStonesInPit;
        }
        return sum;
    }

    /**
     * Once a game has been finished, this will finish calculating who won
     * @param player
     * @return
     */
    public int countStonesInPlayerStore(int player) {
        if (player == 1){
            return boardArray[p1StoreIndex].numberOfStonesInPit;
        } else {
            return boardArray[p2StoreIndex].numberOfStonesInPit;
        }
    }

    /* Allow array to be incremented in a circle - needs to follow a clockwise movement*/
    private void getNextPit(){

        int next = currentPit + 1;
        if (next >= boardArray.length){
            next = 0;
        }
        currentPit = next;
    }

    

   
}
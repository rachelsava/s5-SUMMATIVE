/**
 * This simple class to create and engage with the "blocks" 
 * Overrides fields from Pits to carry out unique behaviours based on conditions listed in enum class (GameMoves)
 */
public class Block extends Pits{

    Block(int owner, int numberOfStonesInPit) {
        super(owner, numberOfStonesInPit);
        lastStoneAddedIntoEmpty = GameMove.STEAL_STONES;
    }
    @Override
    protected int getStoneCount() {
        return numberOfStonesInPit;
    }
    
    @Override
    protected GameMove addStone(int player, int StonesRemaining) {
        numberOfStonesInPit++;
        if (StonesRemaining == 1){   /* final Stone in a pit*/
            if (numberOfStonesInPit == 1) { /* No longer an empty Block*/
                if (player == owner){  /* Player owns this Block*/
                    return lastStoneAddedIntoEmpty;
                }
            }
            return lastStoneAdded;
        }
        return regularStoneAdded;
    }

    /* When a player places their final stone in an empty block that they own, they can steal all stones from opponents corresponding block.*/
    
    @Override
    public int stealStones() {
        int Stones = numberOfStonesInPit;
        numberOfStonesInPit = 0;
        return Stones;
    }

    /* place stones into Block*/
    @Override
    protected void insertLiberatedStones(int StoneNumber) {
        numberOfStonesInPit += StoneNumber;
    }

}
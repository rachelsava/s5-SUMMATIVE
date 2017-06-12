/**
 * A simple Stone store which can only be interacted with by the player who owns it
 */
public class StoneStore extends Pits {
    StoneStore(int owner, int numberOfStonesInPit) {
        super(owner, numberOfStonesInPit);
        lastStoneAdded = GameMove.EXTRA_TURN;
    }

    @Override
    protected GameMove addStone(int player, int StonesRemaining) {
        /* Player can only move through their own store*/
        if (player == owner){
            numberOfStonesInPit++;
            if (StonesRemaining == 1){   /* last Stone*/
                return lastStoneAdded;
            }
            return regularStoneAdded;
        }
        /* Move failed*/
        return null;
    }

    /* Stones taken from another Block*/
    public void insertLiberatedStones (int StoneNumber){
        numberOfStonesInPit += StoneNumber;
    }

    @Override
    protected int getStoneCount() {
        return numberOfStonesInPit;
    }

    /* Not relevant - a downside of abstract inheritance*/
    @Override
    protected int stealStones() {
        return -1;
    }
}
/**
 * Each pit has the same characteristics
 * Created from inside board class
 */
abstract class Pits {

    final int owner;
    int numberOfStonesInPit;
    
    /* Override in child classes to add extra behaviour*/
    final GameMove regularStoneAdded = GameMove.GO_TO_NEXT;
    GameMove lastStoneAdded = GameMove.GO_TO_NEXT;
    GameMove lastStoneAddedIntoEmpty = GameMove.GO_TO_NEXT;

    Pits(int owner, int numberOfStonesInPit) {
        this.owner = owner;
        this.numberOfStonesInPit = numberOfStonesInPit;
    }

    /* Check if current player can add a stone to that block*/
    protected abstract GameMove addStone(int player, int stonesRemaining);

    protected abstract int getStoneCount();

    protected abstract int stealStones();

    protected abstract void insertLiberatedStones(int stoneNumber);

}
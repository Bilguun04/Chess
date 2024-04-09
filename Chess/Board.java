/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package Chess;
import java.util.*;

// line 52 "../../model.ump"
// line 110 "../../model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private List<Position> positions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board()
  {
    positions = new ArrayList<Position>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Position getPosition(int index)
  {
    Position aPosition = positions.get(index);
    return aPosition;
  }

  public List<Position> getPositions()
  {
    List<Position> newPositions = Collections.unmodifiableList(positions);
    return newPositions;
  }

  public int numberOfPositions()
  {
    int number = positions.size();
    return number;
  }

  public boolean hasPositions()
  {
    boolean has = positions.size() > 0;
    return has;
  }

  public int indexOfPosition(Position aPosition)
  {
    int index = positions.indexOf(aPosition);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPositions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Position addPosition(char aX, int aY, Move aMove)
  {
    return new Position(aX, aY, aMove, this);
  }

  public boolean addPosition(Position aPosition)
  {
    boolean wasAdded = false;
    if (positions.contains(aPosition)) { return false; }
    Board existingBoard = aPosition.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
    if (isNewBoard)
    {
      aPosition.setBoard(this);
    }
    else
    {
      positions.add(aPosition);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePosition(Position aPosition)
  {
    boolean wasRemoved = false;
    //Unable to remove aPosition, as it must always have a board
    if (!this.equals(aPosition.getBoard()))
    {
      positions.remove(aPosition);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPositionAt(Position aPosition, int index)
  {  
    boolean wasAdded = false;
    if(addPosition(aPosition))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPositions()) { index = numberOfPositions() - 1; }
      positions.remove(aPosition);
      positions.add(index, aPosition);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePositionAt(Position aPosition, int index)
  {
    boolean wasAdded = false;
    if(positions.contains(aPosition))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPositions()) { index = numberOfPositions() - 1; }
      positions.remove(aPosition);
      positions.add(index, aPosition);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPositionAt(aPosition, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=positions.size(); i > 0; i--)
    {
      Position aPosition = positions.get(i - 1);
      aPosition.delete();
    }
  }

}
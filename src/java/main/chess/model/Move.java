/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package java.main.chess.model;
import java.util.*;

// line 10 "../../model.ump"
// line 127 "../../model.ump"
public class Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Move Associations
  private List<Position> positions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Move()
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
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPositionsValid()
  {
    boolean isValid = numberOfPositions() >= minimumNumberOfPositions() && numberOfPositions() <= maximumNumberOfPositions();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfPositions()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPositions()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPositions()
  {
    return 2;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Position addPosition(char aX, int aY, Board aBoard)
  {
    if (numberOfPositions() >= maximumNumberOfPositions())
    {
      return null;
    }
    else
    {
      return new Position(aX, aY, this, aBoard);
    }
  }

  public boolean addPosition(Position aPosition)
  {
    boolean wasAdded = false;
    if (positions.contains(aPosition)) { return false; }
    if (numberOfPositions() >= maximumNumberOfPositions())
    {
      return wasAdded;
    }

    Move existingMove = aPosition.getMove();
    boolean isNewMove = existingMove != null && !this.equals(existingMove);

    if (isNewMove && existingMove.numberOfPositions() <= minimumNumberOfPositions())
    {
      return wasAdded;
    }

    if (isNewMove)
    {
      aPosition.setMove(this);
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
    //Unable to remove aPosition, as it must always have a move
    if (this.equals(aPosition.getMove()))
    {
      return wasRemoved;
    }

    //move already at minimum (2)
    if (numberOfPositions() <= minimumNumberOfPositions())
    {
      return wasRemoved;
    }
    positions.remove(aPosition);
    wasRemoved = true;
    return wasRemoved;
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
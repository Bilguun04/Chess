/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package java.main.chess.model;

// line 4 "../../model.ump"
// line 70 "../../model.ump"
public class Position
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Position Attributes
  private char x;
  private int y;

  //Position Associations
  private Move move;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Position(char aX, int aY, Move aMove, Board aBoard)
  {
    x = aX;
    y = aY;
    boolean didAddMove = setMove(aMove);
    if (!didAddMove)
    {
      throw new RuntimeException("Unable to create position due to move. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create position due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(char aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public char getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }
  /* Code from template association_GetOne */
  public Move getMove()
  {
    return move;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setMove(Move aMove)
  {
    boolean wasSet = false;
    //Must provide move to position
    if (aMove == null)
    {
      return wasSet;
    }

    //move already at maximum (2)
    if (aMove.numberOfPositions() >= Move.maximumNumberOfPositions())
    {
      return wasSet;
    }
    
    Move existingMove = move;
    move = aMove;
    if (existingMove != null && !existingMove.equals(aMove))
    {
      boolean didRemove = existingMove.removePosition(this);
      if (!didRemove)
      {
        move = existingMove;
        return wasSet;
      }
    }
    move.addPosition(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    if (aBoard == null)
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      existingBoard.removePosition(this);
    }
    board.addPosition(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Move placeholderMove = move;
    this.move = null;
    if(placeholderMove != null)
    {
      placeholderMove.removePosition(this);
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removePosition(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "move = "+(getMove()!=null?Integer.toHexString(System.identityHashCode(getMove())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}
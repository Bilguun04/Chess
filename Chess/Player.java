/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package Chess;

// line 57 "../../model.ump"
// line 116 "../../model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private boolean isWhite;
  private boolean isTurn;

  //Player Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(boolean aIsWhite, boolean aIsTurn, Game aGame)
  {
    isWhite = aIsWhite;
    isTurn = aIsTurn;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsWhite(boolean aIsWhite)
  {
    boolean wasSet = false;
    isWhite = aIsWhite;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsTurn(boolean aIsTurn)
  {
    boolean wasSet = false;
    isTurn = aIsTurn;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsWhite()
  {
    return isWhite;
  }

  public boolean getIsTurn()
  {
    return isTurn;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsWhite()
  {
    return isWhite;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsTurn()
  {
    return isTurn;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removePlayer(this);
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isWhite" + ":" + getIsWhite()+ "," +
            "isTurn" + ":" + getIsTurn()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package java.main.chess.model;

import java.util.*;

// line 15 "../../model.ump"
// line 75 "../../model.ump"
public class Piece
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Piece Attributes
  private String name;
  private boolean iswhite;
  private List<Position> moves;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Piece(String aName, boolean aIswhite, List<Position> aMoves)
  {
    name = aName;
    iswhite = aIswhite;
    moves = aMoves;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setIswhite(boolean aIswhite)
  {
    boolean wasSet = false;
    iswhite = aIswhite;
    wasSet = true;
    return wasSet;
  }

  public boolean setMoves(List<Position> aMoves)
  {
    boolean wasSet = false;
    moves = aMoves;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public boolean getIswhite()
  {
    return iswhite;
  }

  public List<Position> getMoves()
  {
    return moves;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIswhite()
  {
    return iswhite;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "iswhite" + ":" + getIswhite()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "moves" + "=" + (getMoves() != null ? !getMoves().equals(this)  ? getMoves().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
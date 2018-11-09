import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Item> items;        // stores items of this room.
    private HashMap<String, Monster> monsters; // stores monsters of this room.
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new HashMap<String, Item>();
        monsters = new HashMap<String, Monster>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getItemString() + "\n" +
        getMonsterString() + "\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Places a new item in the game and 
     * makes a call to the getDescription() method.
     */
    public void placeItem(Item newItem)
    {
        items.put(newItem.getDescription(), newItem);
    }
    
   /**
    * If the size of an item is greater than zero, return a string
    * of items. 
    * @return the string of an item.
    */ 
   private String getItemString()
   {
        String returnString = "There are no items to be found in this room";
        
        if (items.size() > 0)
        {
            returnString = "Items:";
            Set<String> keys = items.keySet();
            for (String item : keys)
            {
                returnString += item;
                //" <" +  + ">"
            }
        }
        return returnString;
   }
   
   /**
     * Places a new monster in the game and 
     * makes a call to the  method.
     */
    public void placeMonster(Monster newMonster)
    {
       monsters.put(newMonster.getDescription(), newMonster);
    }
    
   /**
    * If the size of a monster is greater than zero, return a string
    * of monsters. 
    * @return the string of a monster.
    */
   private String getMonsterString()
   {
        String returnString = "There are no monsters in this room";
        
        if (monsters.size() > 0)
        {
            returnString = "Monsters:";
            Set<String> keys = monsters.keySet();
            for (String monster : keys)
            {
                returnString += monster;
                // " <" +  + ">"
            }
        }
        return returnString;
   }
}


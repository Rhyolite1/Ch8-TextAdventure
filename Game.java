import java.util.Random;
import java.util.Scanner;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Moretti
 * @version 2018.11.08
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room prevRoom;
    private boolean fighter = false;
    private boolean mage = false;
    private boolean ranger = false;
    private boolean rouge = false;
    private boolean victorious = false;
    private String characterStr;
    private int playerHP;
    private int playerMaxHP;
    private String nameStr;
    
    Scanner Keyboard = new Scanner (System.in);
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrance, throneRoom, greatHall, treasury, solaris,secretRoom,
        kitchens, pantry, waterworks, forge, bakery,undercroft, 
        armory, brewery;
        //Room outside, theater, pub, lab, office;
      
        //  create the rooms
        entrance = new Room("entrance");
        throneRoom = new Room("throneRoom");
        greatHall = new Room("greatHall");
        treasury = new Room("treasury");
        solaris = new Room("solaris");
        secretRoom = new Room("secretRoom");
        kitchens = new Room("kitchens");
        pantry = new Room("pantry");
        waterworks = new Room("waterworks");
        forge = new Room("forge");
        bakery = new Room("bakery");
        undercroft = new Room("undercroft");
        armory = new Room("armory");
        brewery = new Room("brewery");
        
        // initialise room exits
        entrance.setExit("south", greatHall);
        entrance.setExit("east", armory);
        
        greatHall.setExit("north", entrance);
        greatHall.setExit("east", bakery);
        greatHall.setExit("west", solaris);
        greatHall.setExit("south", forge);

        bakery.setExit("north", brewery);
        bakery.setExit("west", greatHall);
        bakery.setExit("south", kitchens);

        kitchens.setExit("north", brewery);
        kitchens.setExit("east", bakery);
        kitchens.setExit("south", pantry);
        
        brewery.setExit("west", kitchens);
        brewery.setExit("south", bakery);

        forge.setExit("north", greatHall);
        forge.setExit("south", waterworks);
        forge.setExit("east", undercroft);
        
        pantry.setExit("north", kitchens);
        
        secretRoom.setExit("east", pantry);
        
        solaris.setExit("south", greatHall);
        
        armory.setExit("west", entrance);
        armory.setExit("north", undercroft);
        armory.setExit("south", waterworks);
        
        undercroft.setExit("south", armory);
        undercroft.setExit("west", forge);
        undercroft.setExit("north", treasury);
        undercroft.setExit("east", waterworks);
        
        waterworks.setExit("north", armory);
        waterworks.setExit("east", forge);
        waterworks.setExit("west", undercroft);
        
        treasury.setExit("south", undercroft);
        treasury.setExit("north", throneRoom);
        
        throneRoom.setExit("west", treasury);
        

        currentRoom = entrance;   //start game outside
        
        
        //initialise game items
        Item Potion = new Item("A flask with fluorescent green liquid inside. " +
        "Might have healing properties", 5);
        Item GoldPieces = new Item("50 gold pieces with the likeness of King"
        + " Jalis", 50);
        Item GiantSapphire = new Item("Giant Sapphire, This 2 foot long gem" +
        " is the biggest you've ever seen", 100);
        
        //specify item location
        secretRoom.placeItem(GoldPieces);
        secretRoom.placeItem(GiantSapphire);
        treasury.placeItem(Potion);
        
        //initalise game monsters
        Monster dragon = new Monster(10000, 25, "Glaurung, the giant dragon");
        Monster skeleton = new Monster(2, 1, "Skeleton Archer");
        Monster goblin = new Monster(4, 2, "Goblin");
        Monster orc = new Monster (1, 3, "Orc");
        Monster necromancer = new Monster (5, 3, "Necromancer");
        
        
         //specify monster location
        throneRoom.placeMonster(dragon);
        undercroft.placeMonster(necromancer);
        kitchens.placeMonster(skeleton);
        waterworks.placeMonster(orc);
        treasury.placeMonster(goblin);
        
    
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the world of Ageishya");
        System.out.println("The Dwarves were a very prosperous fortress" + 
        " of Yao'Sai stood as the " +
        "pinicale of");
        
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        enterName();
        chooseCharacter();
        System.out.println(currentRoom.getLongDescription());
    }
    
    private void chooseCharacter()
    {
        boolean chosen = false;
        System.out.println("Choose your Character: Fighter, Ranger or Mage");
        characterStr = Keyboard.nextLine().trim().toUpperCase();
        while (chosen !=true)
        {
            if(characterStr.equals("FIGHTER"))
            {
                chosen = true;
                fighter = true;
            }
            else if (characterStr.equals("MAGE"))
            {
                chosen = true;
                mage = true;
            }
            else if(characterStr.equals("RANGER"))
            {
                chosen = true;
                ranger = true;
            }
            
            else if (characterStr.equals("ROUGE") 
            && (nameStr.equals("ISABELLA")||victorious == true))
            {
                chosen = true;
                rouge = true;
            }
           else
           {
           System.out.println("Invalid Choice Please Try Again: ");
           characterStr = Keyboard.nextLine().trim().toUpperCase();
           }
        }
        
    }
    

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("Umm what?");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
            
            case LOOK:
                look();
                break;
                
            case ATTACK:
                attack(command);
                break;
                
            case GIVE:
                give(command);
                break;
                
            case CONSUME:
                consume(command);
                break;
                
            case BACK:
                goBack();
                break;
            
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are an explorer sent to investigate Yao'sai");
        System.out.println("find out what happened to the dwarves.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
    * Checks to see if a previous room is available to go back into.
    * If the program can not find such a room, 
    * the user has to find another way out.
    * Otherwise, the user will be able to go back to the previous room.
    */
    private void goBack()
    {
        if (prevRoom == null)
        {
            System.out.println("it appears that there are no rooms " + 
            "to go back to. Find another way out!");
        }
        else
        {
            currentRoom = prevRoom;
            System.out.println("You have gone back to: " + 
            "\n" + currentRoom.getLongDescription());
        }
    }

    /**
     * looks to see which room the user is currently in
     * and prints out a description.
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    private void attack(Command command)
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to attack
            System.out.println("attack what?");
            return;
        }
    }
        
    public void dead()
    {
        System.out.println("You are dead");
    System.out.println("You are respawned thanks to your amulet.");
        goBack();
    playerHP = 20;
    }
    
   /**
    * Give an item to a NPC
    * 
    * @params the item you wish to give
    */
    public void give(Command command)
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to give
            System.out.println("give what?");
            return;
        }
    }
    
    /**
     * Consume an item
     * 
     * @params the item to be consumed
     */
    public void consume(Command command)
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to consume
            System.out.println("this isn't a buffet, " + 
            "you can't consume everything");
            return;
        }
    }
    
    /**
     * Takes in the name of the player
     */
    public void enterName()
    {
    System.out.print("What is your name?");
        nameStr = Keyboard.nextLine().trim().toUpperCase();
        if(nameStr.equals("ISABELLA"))
        {
            System.out.println("Isabella, as thanks for inspiring me you can" +
            "access one of this game's hidden features early.");
            System.out.println("you have access to the rouge class, enjoy!");
        }
    }
    
    /**
     * Allows the user to run the game in the main method 
     * rather than through BlueJ.
     */
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
}

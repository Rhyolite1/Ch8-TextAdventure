import java.util.Random;
/**
 * Write a description of class Monster here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Monster
{
    // instance variables - replace the example below with your own
    private int health;
    private int attack;
    private String description;
    Random rand = new Random();

    /**
     * Constructor creates monsters for the game
     */
    public Monster(int health, int attack, String description)
    {
        this.health = health;
        this.attack = attack;
        this.description = description;
    }
    
    /**
     * Takes the private value monsterHealth and makes plublicly viewable
     *
     * @return    Health
     */
    public int getHealth()
    {
        return health;
    }
    
    /**
     * Takes the private value monsterAttack and makes plublicly viewable
     *
     * @return    Attack
     */
    public int getAttack()
    {
        return attack;
    }
    
    /**
     * Takes the private value monsterDescription and makes plublicly viewable
     *
     * @return    monsterDescription
     */
    public String getDescription()
    {
        return description;
    } 
}


/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String description;
    private int weight;
    
    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
    }

    public String getDescription()
    {
        return description;
    } 

     public int getWeight()
    {
        return weight;
    }

} 
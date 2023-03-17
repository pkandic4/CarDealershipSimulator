import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Records information about the dealership's sales team.
 * @author Petar Kandic 500876167
 */

public class SalesTeam
{

    /**
     * The linked list contains the sales people.
     * Random object created, used to generate a random sales person below.
     */

    private LinkedList<String> salesMembers;
    private Random random;

    /**
     * Constructor method.
     * Instantiates salesMembers and random, then adds five not-so-random names.
     */

    public SalesTeam()
    {
        salesMembers = new LinkedList<String>();
        random = new Random();
        salesMembers.add("Hank");
        salesMembers.add("Dale");
        salesMembers.add("Bill");
        salesMembers.add("Boomhauer");
        salesMembers.add("Bobby");
    }

    /**
     * A random int from 0 to the linked list's size is generated.
     * Then a random sales person is picked from the list and returned.
     * @return a random sales person.
     */

    public String getRandomSalesperson()
    {
        int randomIndex = random.nextInt(salesMembers.size());
        return salesMembers.get(randomIndex);
    }

    /**
     * A ListIterator is used to "extract" sales people from the list,
     * and to determine if more are remaining. If not, the loop ends.
     * @return the sales people
     */

    public String displaySalespeople()
    {
        ListIterator<String> iterateSales = salesMembers.listIterator();
        String result = "";
        while(iterateSales.hasNext())
        {
            result += iterateSales.next() + "\n";
        }
        return result;
    }

    /**
     * Uses LinkedList size method to find the number of people on the team.
     * @return a String message including the sales team's size
     */

    public String salesTeamSize()
    {
        return "There are " + salesMembers.size() + " sales people on the team.";
    }

    public String findSalespersonAtIndex(int index) throws IndexOutOfBoundsException
    {
        String result;
        if (index < 0 || index > salesMembers.size())
        {
           result = "Hey, that's out of range! Try again.";
        }
        else
        {
            result = salesMembers.get(index);
        }
        return result;
    }
}

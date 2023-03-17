import java.util.*;

/**
 * This class generates "receipts" for when cars are bought and returned.
 * It also stores this info in a map.
 * @author Petar Kandic 500876167
 */

public class AccountingSystem
{

    /**
     * Note the map to store transaction info.
     * Random and Transaction objects created.
     */

    private Map<Integer, Transaction> listTransactions;
    private Random random;
    private Transaction transaction;

    /**
     * Constructor method.
     * listTransactions initialized as TreeMap. random initialized.
     */

    public AccountingSystem()
    {
        this.listTransactions = new TreeMap<Integer, Transaction>();
        random = new Random();
    }

    /**
     * A random number between 1 and 99 is generated, to be used as an index value used returning a car.
     * Takes its parameters and stores them in transaction, which is then stored in the map,
     * with randomID as a key for each transaction.
     * @param date
     * @param car
     * @param salesPerson
     * @param type
     * @param salePrice
     * @return a String, specifically transaction.display()
     */

    public String add(Calendar date, Car car, String salesPerson, String type, double salePrice)
    {
        int randomID = random.nextInt(99) + 1;
        transaction = new Transaction(randomID, date, car, type, salePrice, salesPerson);
        listTransactions.put(randomID, transaction);
        return transaction.display();
    }

    /**
     * Obtains a Transaction from the map given an id (the key).
     * @param id
     * @return a Transaction mapped to id
     */

    public Transaction getTransaction(int id)
    {
        Transaction transaction = listTransactions.get(id);
        return transaction;
    }

    /**
     * Passes the values of listTransactions to an ArrayList,
     * so that the transactions can be easily displayed elsewhere.
     * @return an ArrayList of Transactions
     */


    public ArrayList<Transaction> allTransactions()
    {
        ArrayList<Transaction> arrListTransactions = new ArrayList<Transaction>();
        arrListTransactions.addAll(listTransactions.values());
        return arrListTransactions;
    }
}

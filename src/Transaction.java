import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Stores information about bought and returned cars.
 * @author Petar Kandic 500876167
 */

public class Transaction
{

    /**
     * These instance variables store info about cars involved in transactions.
     * That is, info about cars that have been bought or returned (or both).
     * So, that means transType can be either "buy" or "ret".
     */

    private int transID;
    private Calendar date;
    private Car car;
    private String transType;
    private double price;
    private String salesPerson;

    /**
     * Constructor method.
     * Initializes all instance fields above.
     * @param transID
     * @param date
     * @param car
     * @param transType
     * @param price
     * @param salesPerson
     */

    public Transaction(int transID,  Calendar date, Car car, String transType, double price, String salesPerson)
    {
        this.transID = transID;
        this.date = date;
        this.car = car;
        this.transType = transType;
        this.price = price;
        this.salesPerson = salesPerson;
    }

    /**
     * Using SimpleDateFormat, the date is formatted as seen below.
     * The date is then rendered as a string.
     * Information about the transaction is stored in a string, as well as info
     * about the car.
     * @return a string containing information described above.
     */

    public String display()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        String dateString = sdf.format(date.getTime());

        String s = "ID: " + transID + " Date: " + dateString + " " + transType.toUpperCase() + " "
                + "Salesperson: " + salesPerson + " Car Info: " + car.display();
        return s;
    }

    /**
     * Getter method for the transaction type.
     * @return either "buy" or "ret"
     */

    public String getTransType()
    {
        return transType;
    }

    /**
     * Getter method for the date.
     * @return a date (yyyy mm dd)
     */

    public Calendar getDate() {
        return date;
    }

    /**
     * Getter method for the Car object.
     * @return a Car object
     */

    public Car getCar()
    {
        return car;
    }

    /**
     * Getter method for the sales person.
     * @return a sales person
     */

    public String getSalesPerson()
    {
        return salesPerson;
    }

    /**
     * Getter method for the price.
     * @return the price
     */

    public double getPrice()
    {
        return price;
    }
}

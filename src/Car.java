import java.util.Objects;

/**
 * Represents a car with several attributes.
 * Extends Vehicle and implements Comparable.
 * NO ALTERATIONS HAVE BEEN MADE TO CAR.
 * NO CHANGES TO JAVADOC MADE.
 * @author Petar Kandic 500876167
 */

class Car extends Vehicle implements Comparable<Car>
{

    /**
     * Instance variables that represent key properties a car has.
     * Note the enum model - contains four (string) values defined below.
     */

    private Model model;
    private int maxRange;
    private double safetyRating, price;
    private boolean AWD;


    /**
     * Enum for model variable.
     * Here, a model may be one of these four strings.
     */

    public enum Model
    {
        SEDAN,
        SUV,
        SPORTS,
        MINIVAN
    }

    /**
     * Constructor for the Car class.
     * Invokes Vehicle's constructor via super call.
     * @param mfr
     * @param color
     * @param power
     * @param numWheels
     * @param model
     * @param maxRange
     * @param safetyRating
     * @param price
     * @param AWD
     */

    public Car(String mfr, String color, int power, int numWheels, Model model,
               int maxRange, double safetyRating, double price, boolean AWD)
    {
        super(mfr, color, power, numWheels);
        this.model = model;
        this.maxRange = maxRange;
        this.safetyRating = safetyRating;
        this.price = price;
        this.AWD = AWD;
    }

    /**
     * Getter method for the car model
     * @return the model of the car
     */

    public Model getModel()
    {
        return model;
    }

    /**
     * Setter method for the car model
     * @param model
     */

    public void setModel(Model model)
    {
        this.model = model;
    }

    /**
     * Getter method for the car's maximum range
     * @return the max range
     */

    public int getMaxRange()
    {
        return maxRange;
    }

    /**
     * Setter method for the max range
     * @param maxRange
     */

    public void setMaxRange(int maxRange)
    {
        this.maxRange = maxRange;
    }

    /**
     * Getter method for the car's safety rating
     * @return the safety rating
     */

    public double getSafetyRating()
    {
        return safetyRating;
    }

    /**
     * Setter method for the safety rating
     * @param safetyRating
     */

    public void setSafetyRating(double safetyRating)
    {
        this.safetyRating = safetyRating;
    }

    /**
     * Getter method for the car's price
     * @return the price of the car
     */

    public double getPrice()
    {
        return price;
    }

    /**
     * Setter method for the car model
     * @param price
     */

    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Getter method that returns whether or not the car has AWD
     * @return true (car has AWD) or false (it does not)
     */

    public boolean isAWD()
    {
        return AWD;
    }

    /**
     * Setter method for the AWD variable
     * @param AWD
     */

    public void setAWD(boolean AWD)
    {
        this.AWD = AWD;
    }

    /**
     * Displays the car's attributes.
     * Includes all instance variables in this class, as well as mfr and color variables of superclass -
     * note the super call (and override).
     * @return string representation of all above variables, each separated with a space
     */

    @Override
    public String display()
    {
        String s = (model + " " + maxRange + " " + safetyRating + " " + price + " " + AWD);
        return super.display() + " " + s;
    }

    /**
     * Follows much the same logic as the equals method in Vehicle.
     * Here, another requirement is that other equal a Vehicle object (note super), as Car is a subclass of Vehicle.
     * Car objects are equal if their AWD and model variables are equal.
     * @param other (object)
     * @return true (objects are equal) or false (they are not equal)
     */

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (!(other instanceof Car))
        {
            return false;
        }
        if (!super.equals(other))
        {
            return false;
        }
        Car car = (Car) other;
        return AWD == car.AWD && Objects.equals(model, car.model);
    }

    /**
     * Implements compareTo method of Comparable interface.
     * Price variable of Car object other is compared to price variable of this (keyword) object.
     * @param other
     * @return -1, 0, or 1, depending on result of algorithm
     */

    public int compareTo(Car other)
    {
        if (this.price < other.price)
        {
            return -1;
        }
        else if (this.price > other.price)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

}

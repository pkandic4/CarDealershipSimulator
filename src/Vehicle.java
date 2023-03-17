import java.util.Objects;
import java.util.Random;

/**
 * Represents a base vehicle.
 * NEW TO A2: Only alterations made to Javadoc are marked below as NEW TO A2 (so you can search).
 * @author Petar Kandic, 500876167
 */

public class Vehicle
{

    /**
     * Instance variables for the Vehicle class.
     * First four represent essential characteristics (note mfr = manufacturer).
     * Note ELECTRIC_MOTOR & GAS_ENGINE; these are used as values for variable power.
     * They are used to determine what the vehicle "runs" on.
     * NEW TO A2: The VIN variable has been added.
     * A Random object created, to be used to generate a random VIN below.
     */

    private String mfr, color;
    private int power, numWheels, VIN;
    public static final int ELECTRIC_MOTOR = 0;
    public static final int GAS_ENGINE = 1;
    Random random;

    /**
     * Getter method for the vehicle manufacturer.
     * @return the manufacturer
     */

    public String getMfr()
    {
        return mfr;
    }

    /**
     * Setter method for the vehicle manufacturer.
     * @param mfr (manufacturer)
     */

    public void setMfr(String mfr)
    {
        this.mfr = mfr;
    }

    /**
     * Getter method for the power (that is, whether the vehicle has an electric motor (0) or gas engine (1)).
     * @return the power (0 or 1)
     */

    public int getPower()
    {
        return power;
    }

    /**
     * setter method for the power variable.
     * @param power
     */

    public void setPower(int power)
    {
        this.power = power;
    }

    /**
     * Getter method for the number of wheels the vehicle has.
     * @return number of wheels
     */

    public int getNumWheels()
    {
        return numWheels;
    }

    /**
     * Setter method for the number of wheels.
     * @param numWheels
     */

    public void setNumWheels(int numWheels)
    {
        this.numWheels = numWheels;
    }

    /**
     * Getter method for the colo(u)r of the vehicle.
     * @return the color
     */

    public String getColor()
    {
        return color;
    }

    /**
     * Setter method for the vehicle color.
     * @param color
     */

    public void setColor(String color)
    {
        this.color = color;
    }

    /**
     * Getter method for the VIN.
     * @return the VIN
     */

    public int getVIN()
    {
        return VIN;
    }

    /**
     * Setter method for the VIN.
     * @param VIN
     */

    public void setVIN(int VIN)
    {
        this.VIN = VIN;
    }

    /**
     * Constructor for the Vehicle class.
     * NEW TO A2: Generates a random VIN between 100 and 499
     * (note nextInt argument is exclusive).
     * Also, note initialization of random.
     * @param mfr
     * @param color
     * @param power
     * @param numWheels
     */

    public Vehicle(String mfr, String color, int power, int numWheels)
    {
        this.mfr = mfr;
        this.color = color;
        this.power = power;
        this.numWheels = numWheels;
        random = new Random();
        this.VIN = random.nextInt(400) + 100;
    }

    /**
     * Equals method - note it is overwriting the equals method in java.lang.Object.
     * This method compares two objects; if their power, numWheels, and mfr variables are equal,
     * then the two objects are equal. Otherwise, they are not.
     * Note that the object parameter is first checked to determine if it is equal to a Vehicle object
     * It is then checked to ensure that it is an instance of Vehicle.
     * Passing both these requirements, other is casted to Vehicle, and the above variables are compared.
     * @param other (object)
     * @return true (objects are equal) or false (they are not equal)
     */

    @Override
    public boolean equals(Object other)
    {
        if (this == other) return true;
        if (!(other instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) other;
        return power == vehicle.power &&
                numWheels == vehicle.numWheels &&
                Objects.equals(mfr, vehicle.mfr);
    }

    /**
     * Displays the vehicle's manufacturer and color, separated with a space.
     * NEW TO A2: Now includes VIN at the beginning.
     * @return string representation of three variables above (with a space)
     */

    public String display()
    {
        return (VIN + " " + mfr + " " + color);
    }
}






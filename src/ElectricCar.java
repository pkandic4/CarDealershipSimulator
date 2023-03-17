/**
 * Represents an electric car with two unique attributes.
 * Extends Car class.
 * NO ALTERATIONS HAVE BEEN MADE TO ELECTRICCAR.
 * NO CHANGES TO JAVADOC MADE.
 * @author Petar Kandic 500876167
 */

public class ElectricCar extends Car
{

    /**
     * Two instance variables, one representing the recharge time of an electric motor,
     * and another one for battery type.
     */

    private int rechargeTime;
    private String batteryType;

    /**
     * Constructor for ElectricCar class.
     * Invokes constructor for superclass Car via super call.
     * @param mfr
     * @param color
     * @param power
     * @param numWheels
     * @param model
     * @param maxRange
     * @param safetyRating
     * @param price
     * @param AWD
     * @param rechargeTime
     * @param batteryType
     */

    public ElectricCar(String mfr, String color, int power, int numWheels, Model model, int maxRange,
                       double safetyRating, double price, boolean AWD, String batteryType, int rechargeTime)
    {
        super(mfr, color, power, numWheels, model, maxRange, safetyRating, price, AWD);
        this.rechargeTime = rechargeTime;
        this.batteryType = batteryType;
    }

    /**
     * Getter method for the electric car's recharge time
     * @return the recharge time
     */

    public int getRechargeTime()
    {
        return rechargeTime;
    }

    /**
     * Setter method for the electric car's recharge time
     * @param rechargeTime
     */

    public void setRechargeTime(int rechargeTime)
    {
        this.rechargeTime = rechargeTime;
    }

    /**
     * Getter method for the electric car's battery type
     * @return the battery type
     */

    public String getBatteryType()
    {
        return batteryType;
    }

    /**
     * Setter method for the battery type
     * @param batteryType
     */

    public void setBatteryType(String batteryType)
    {
        this.batteryType = batteryType;
    }

    /**
     * Invokes Car's display method (super) and displays the two instance variables defined above,
     * all separated with a space.
     * @return string representation of above variables + variables in Car's display, with spaces.
     */

    @Override
    public String display()
    {
        String newVars = " " + rechargeTime + " " + batteryType + " ";
        return super.display() + newVars + " ";
    }

}

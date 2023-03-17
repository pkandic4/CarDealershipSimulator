import java.util.*;

/**
 * Houses numerous methods to be called from the simulator class.
 * NEW TO A2: Javadoc edits discussed below, beginning with NEW TO A2 (so you can search).
 * @author Petar Kandic 500876167
 */

public class CarDealership
{
    /**
     * Instance variables including an empty array list of cars. This holds the cars from cars.txt.
     * Filter variables are toggled by user during runtime (through filter methods described below).
     * They filter the list of cars based on various properties,
     * here that is the price, whether the car has AWD, and whether it has an electric motor or not.
     * NEW TO A2: Instances of SalesTeam and AccountingSystem (plus a Random object).
     */

    private ArrayList<Car> carList;
    private boolean AWDFilterIsSet, electricFilterIsSet, priceFilterIsSet;
    private double minPriceFilter, maxPriceFilter;
    private AccountingSystem accSystem;
    private SalesTeam salesTeam;
    private Random random;

    /**
     * Constructor method.
     * NEW TO A2: Now initializes salesTeam and accSystem (to avoid NullPointerExceptions when they are used).
     * @param carList
     */

    public CarDealership(ArrayList<Car> carList)
    {
        this.carList = carList;
        random = new Random();
        salesTeam = new SalesTeam();
        accSystem = new AccountingSystem();
    }

    /**
     * NEW TO A2: Getter method for accSystem.
     * Used in several CarDealershipSimulator methods,
     * to access transactions (more on that later).
     * @return accSystem
     */

    public AccountingSystem getAccSystem()
    {
        return accSystem;
    }

    /**
     * Adds the cars from cars.txt to this class's carList.
     * @param newCars
     */

    public void addCars(ArrayList<Car> newCars)
    {
        carList.addAll(newCars);
        System.out.println("cars.txt added!");
    }

    /**
     * Buys (removes) car at specified index.
     * Thrown exception caught in simulator class, at buyCommand().
     * NEW TO A2: Checks if the passed VIN is outside permissible range, and if the carList is empty.
     * If either are true, exception thrown.
     * If both of these are "valid", a random date in 2019 is set, and a transaction record is obtained.
     * The bought car is removed, and a random sales person is obtained.
     * This info, among others, is passed to AccountSystem for recording.
     * Finally, the transaction info is displayed, as well as info on the purchased car, which is returned.
     * @param VIN
     * @return the purchased car
     * @throws IndexOutOfBoundsException
     */

    public Car buyCar(int VIN) throws IndexOutOfBoundsException
    {
        if (VIN < 100 || VIN > 499)
        {
            throw new IndexOutOfBoundsException("VIN out of range!");
        }
        if (carList.isEmpty())
        {
            throw new IndexOutOfBoundsException("The list of cars is empty!");
        }
        for (Car car : carList)
        {
            if (VIN == car.getVIN())
            {
                int month = random.nextInt(12);
                int day = random.nextInt(31) + 1;
                Calendar calendar = new GregorianCalendar();
                calendar.set(2022, month, day);

                carList.remove(car);
                String randomSalesperson = salesTeam.getRandomSalesperson();
                String bought = accSystem.add(calendar, car, randomSalesperson, "BUY", car.getPrice());
                System.out.println(bought);
                return car;
            }
        }
        return null;
    }

    /**
     * Returns the last car bought.
     * That is, adds it back to the end of the list.
     * NEW TO A2: Obtains a purchased car from accSystem using a randomly generated ID.
     * This is checked to see if it is null. If so, exception thrown.
     * A new day is generated, which is then "added" to the bought car's date,
     * ensuring the return date is later than the buying date.
     * The returned car is re-added to carList via getCar(), and the returned car is obtained and printed.
     * @param transID
     */

    public void returnCar(int transID) throws IndexOutOfBoundsException
    {
        Transaction buyTransaction = accSystem.getTransaction(transID);
        if (buyTransaction == null)
        {
            throw new IndexOutOfBoundsException("No such transaction found!");
        }
        int laterDay = random.nextInt(20) + 1;

        Calendar laterDate = new GregorianCalendar();
        laterDate.setTime(buyTransaction.getDate().getTime());
        laterDate.add(Calendar.DATE, laterDay);
        carList.add(buyTransaction.getCar());
        String returned = accSystem.add(laterDate, buyTransaction.getCar(), salesTeam.getRandomSalesperson(), "RET",
                buyTransaction.getCar().getPrice());
        System.out.print(returned);
    }

    /**
     * Here, carList is scanned for filters.
     * If there is a car that complies with all filters, it is added to goodCars.
     * @return an array of filter-compliant cars
     */

    private ArrayList<Car> getFilteredInventory()
    {
        ArrayList<Car> goodCars = new ArrayList<>();
        for (Car car : carList)
        {
            if (shouldCarBeShownBasedOnAWDFilter(car) &&
                    shouldCarBeShownBasedOnElectricFilter(car) &&
                    shouldCarBeShownBasedOnPriceFilter(car))
            {
                //adding only 'compatible' cars to the list
                goodCars.add(car);
            }
        }
        return goodCars;
    }

    /**
     * The main display method of the program.
     * If the list is empty (due to filters or if cars.txt has not been added), appropriate text displayed.
     * If the list is not empty, the legend is printed (using formatted printing).
     * Then we format print variables from Car & ElectricCar using a for loop.
     * NEW TO A2: VIN printed instead of the original index.
     */

    public void displayInventory()
    {
        ArrayList<Car> goodCars = getFilteredInventory();
        if (goodCars.isEmpty())
        {
            System.out.println("No cars available!");
        }
        else
            {
        System.out.format("%4s %12s %8s %12s %9s %13s %15s %12s %5s %12s %13s %12s\n",
                "VIN",
                "Manufacturer",
                "Model",
                "Color",
                "Max Range",
                "Safety Rating",
                "Electric motor?",
                "Price",
                "AWD?",
                "# of wheels",
                "Recharge time",
                "Battery Type");
        for (int q = 0; q <goodCars.size(); q++)
        {
            Car car = goodCars.get(q);
            String batteryType = "N/A";
            int rechargeTime = 0;
            if(car instanceof ElectricCar)
            {
                batteryType = ((ElectricCar)car).getBatteryType();
                rechargeTime = ((ElectricCar)car).getRechargeTime();
            }

            System.out.format("%4d %12s %8s %12s %,9d %13.1f %15b %,12.2f %5b %12d %13d %12s\n",
                    car.getVIN(),
                    car.getMfr(),
                    car.getModel(),
                    car.getColor(),
                    car.getMaxRange(),
                    car.getSafetyRating(),
                    //Boolean, either they are equal or they are not
                    car.getPower() == Vehicle.ELECTRIC_MOTOR,
                    car.getPrice(),
                    car.isAWD(),
                    car.getNumWheels(),
                    rechargeTime,
                    batteryType);
        }
        }
    }

    /**
     * As one could guess, if AWD filter is set, car will be shown if it has AWD.
     * If filter is set but car is NOT AWD, the car won't be shown.
     * Note for all three of these methods (xFilterIsSet) variables are toggled from simulator class, by user.
     * @param car
     * @return boolean value determining if car should be shown based on this filter.
     */

    private boolean shouldCarBeShownBasedOnAWDFilter(Car car)
    {
        if (AWDFilterIsSet)
        {
            if (car.isAWD())
            {
                return true;
            }
            else
                {
                return false;
            }
        }
        else
            {
            return true;
        }
    }

    /**
     * Same as above method, for electric motors.
     * @param car
     * @return boolean value determining if car should be shown based on this filter.
     */

    private boolean shouldCarBeShownBasedOnElectricFilter(Car car)
    {
        if (electricFilterIsSet)
        {
            if (car.getPower() == Vehicle.ELECTRIC_MOTOR)
            {
                return true;
            }
            else
                {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * Same as above method, for price filters.
     * Note this includes min and max ranges for the price.
     * @param car
     * @return boolean value determining if car should be shown based on these two filters.
     */

    private boolean shouldCarBeShownBasedOnPriceFilter(Car car)
    {
        if (priceFilterIsSet)
        {
            if ((car.getPrice() >= minPriceFilter) && (car.getPrice() <= maxPriceFilter))
            {
                return true;
            }
            else
                {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * All of these boolean instance variables default to false.
     * In setting them to true, the shouldxbeshown... methods above are "activated" (note if statements)
     * , thus setting the filters.
     * This method is a filter for electric motors.
     */

    public void filterByElectric()
    {
        electricFilterIsSet = true;
    }

    /**
     * Filter for AWD.
     */

    public void filterByAWD()
    {
        AWDFilterIsSet = true;
    }

    /**
     * Filter for price, including minimum and maximum limits.
     * @param minPrice
     * @param maxPrice
     */

    public void filterByPrice(double minPrice, double maxPrice)
    {
        priceFilterIsSet = true;
        minPriceFilter = minPrice;
        maxPriceFilter = maxPrice;
    }

    /**
     * Sets all boolean filter variables to false again.
     * This results in the if statements in the shouldxbeshown... methods being passed over.
     */

    public void FiltersClear()
    {
        electricFilterIsSet = false;
        AWDFilterIsSet = false;
        priceFilterIsSet = false;
    }

    /**
     * All three methods below call their respective inner classes for appropriate sorting.
     * These classes are discussed below.
     * This method sorts using ComparatorByPrice.
     */

    public void sortByPrice()
    {
        carList.sort(new ComparatorByPrice());
    }

    /**
     * This method sorts using ComparatorBySafetyRating.
     */

    public void sortBySafetyRating()
    {
        carList.sort(new ComparatorBySafetyRating());
    }

    /**
     * This method sorts using ComparatorByMaxRange.
     */

    public void sortByMaxRange()
    {
        carList.sort(new ComparatorByMaxRange());
    }

    /**
     * Inner classes. All implement the Comparator interface.
     * The compare method of this interface takes any two car objects from the list,
     * comparing them based on one of three qualities (for each class).
     * This class sorts uses compare to sort by price.
     */

    class ComparatorByPrice implements Comparator<Car>
    {
        public int compare(Car c1, Car c2)
        {
            if (c1.getPrice() < c2.getPrice())
            {
                return -1;
            }
            else if (c1.getPrice() > c2.getPrice())
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

    /**
     * Same as above class, for safety rating sorting.
     */

    class ComparatorBySafetyRating implements Comparator<Car>
    {
        public int compare(Car c1, Car c2)
        {
            if (c1.getSafetyRating() < c2.getSafetyRating())
            {
                return -1;
            }
            else if (c1.getSafetyRating() > c2.getSafetyRating())
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

    /**
     * Same as above class, for max range sorting.
     */

    class ComparatorByMaxRange implements Comparator<Car>
    {
        public int compare(Car c1, Car c2)
        {
            if (c1.getMaxRange() < c2.getMaxRange())
            {
                return -1;
            }
            else if (c1.getMaxRange() > c2.getMaxRange())
            {
                return 1;
            }
            else
                {
                return 0;
            }
        }
    }

    /**
     * Returns carList.
     * @return the list of cars
     */

    public ArrayList<Car> getCarList()
    {
        return carList;
    }
}


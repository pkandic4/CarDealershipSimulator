import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * User inputs are processed here. Methods from CarDealership are called.
 * NEW TO A2: Changes marked as such in the javadoc.
 * @author Petar Kandic 500876167
 */

public class CarDealershipSimulator
{
    /**
     * CarDealership and Car objects, used to access respective classes.
     * lastCarBought is used for the buy and return methods.
     * NEW TO A2: SalesTeam object. Instantiated here are there is no constructor.
     */

    private static CarDealership carDealership;
    private static CarDealershipSimulator cds;
    private Car lastCarBought;
    private SalesTeam salesTeam = new SalesTeam();

    /**
     * The main method processes the user's input.
     * First a message and a list of valid commands are displayed.
     * A scanner object is used to separate the strings from the input.
     * The first such string is assigned to the command variable and is the most important of the input strings.
     * Inside a while loop, if statements are used for input processing.
     * Appropriate methods further in the class are called for the corresponding inputs.
     * Note the String array commandAndParameters.
     * This is used for the buy and price filter commands, which require more than one input string,
     * passing the array to the appropriate methods.
     * NEW TO A2: a try-catch block surrounds the if statement block, to catch any exceptions.
     * All exceptions are caught in this method. When this happens,
     * e.getMessage() is used to print correct messages for certain exceptions.
     * Note that now, several sales commands require use of commandAndParameters
     * (though only one of the invoked methods takes it as an argument).
     */

    public static void main(String[] args)
    {
        cds = new CarDealershipSimulator();
        cds.createDealership();
        cds.showAvailableCommands();
        boolean end = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Car Dealership Simulator!");
        while (!end)
            try
            {
                {
                    String nextLine = scan.nextLine();
                    String commandAndParameters[] = nextLine.split(" ");
                    String command = commandAndParameters[0];
                    if (command.equalsIgnoreCase("L"))
                    {
                        cds.runListCommand();
                    }
                else if (command.equalsIgnoreCase("q"))
                {
                    //breaks the loop
                    end = true;
                }
                else if (command.equalsIgnoreCase("buy"))
                {
                    //Note array is passed - multiple input strings needed
                    cds.buyCommand(commandAndParameters);
                }
                else if (command.equalsIgnoreCase("ret"))
                {
                    cds.returnCommand(commandAndParameters);
                }
                else if (command.equalsIgnoreCase("add"))
                {
                    ArrayList<Car> toAdd = cds.loadFromFile();
                    carDealership.addCars(toAdd);
                }
                else if(command.equalsIgnoreCase("sales") && commandAndParameters.length == 1)
                {
                    cds.printTransactions();
                }
                else if(command.equalsIgnoreCase("sales") && commandAndParameters.length == 2)
                {
                    if(commandAndParameters[1].equalsIgnoreCase("team"))
                    {
                        cds.printSalesTeam();
                    }
                    else if(commandAndParameters[1].equalsIgnoreCase("topsp"))
                    {
                        cds.printTopSalesperson();
                    }
                    else if (commandAndParameters[1].equalsIgnoreCase("stats"))
                    {
                        cds.printStats();
                    }
                    else
                    {
                        try
                        {
                            int intCommand = Integer.parseInt(commandAndParameters[1]);
                            //Not > 11 - see monthsTransaction below
                            if (intCommand < 0 || intCommand > 12)
                            {
                                System.out.println("Month is out of range!");
                            }
                            cds.monthsTransactions(intCommand);
                        }
                        catch (NumberFormatException e)
                        {
                            throw new InvalidParameterException("Unknown command " + commandAndParameters[1]);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("spr"))
                {
                    cds.sortedPriceCommand();
                }
                else if (command.equalsIgnoreCase("ssr"))
                {
                    cds.sortedSafetyCommand();
                }
                else if (command.equalsIgnoreCase("smr"))
                {
                    cds.sortedRangeCommand();
                }
                else if (command.equalsIgnoreCase("fpr"))
                {
                    //Note array is passed - multiple input strings needed
                    cds.filterPriceCommand(commandAndParameters);
                }
                else if (command.equalsIgnoreCase("fel"))
                {
                    cds.filterElectricCommand();
                }
                else if (command.equalsIgnoreCase("faw"))
                {
                    cds.filterAWDCommand();
                }
                else if (command.equalsIgnoreCase("fcl"))
                {
                    cds.filterClearCommand();
                }
                else
                    {
                    System.out.println("That's not a valid command!");
                    cds.showAvailableCommands();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception caught: " + e.getMessage());
        }
        System.out.println("Thank you, goodbye!");
    }

    /**
     * Initializes carDealership object.
     */

    private void createDealership()
    {
        carDealership = new CarDealership(new ArrayList<>());
    }

    /**
     * Determines if cars.txt has been added. If it has, inventory is displayed.
     * If not, error message printed, as there are no cars to display.
     */

    private void runListCommand()
    {
        if (carDealership.getCarList() == null || carDealership.getCarList().isEmpty())
        {
            System.out.println("You haven't added the list! Run add to get cars");
        }
        carDealership.displayInventory();

    }

    /**
     * Uses the second input string to determine the VIN of the car to be bought, which is passed to buyCar().
     * If the index is in range, lastCarBought is initialized as the Car at the given index.
     * Note the exceptions. The try catch block here throws a unique Exception -
     * the one being caught is that thrown in CarDealership's buyCar method.
     * NEW TO A2: now works with VIN, otherwise identical.
     * @param commandAndParameters
     * @throws Exception
     */

    private void buyCommand(String [] commandAndParameters) throws Exception
    {
        //second input string
        int VIN = Integer.parseInt(commandAndParameters[1]);
        Car car = carDealership.buyCar(VIN);
        if (car == null)
        {
           throw new Exception("No car with that VIN could be found!");
        }
        else
        {
            System.out.println("You bought a " + car.getMfr() + "!");
            lastCarBought = car;
        }
    }

    /**
     * NEW TO A2: VIN taken as a parameter.
     * If lastCarBought is null, error message printed.
     * If lastCarBought is not null, VIN passed to returnCar,
     * and the car is then displayed with a message.
     */

    private void returnCommand(String [] commandAndParameters)
    {
        int VIN = Integer.parseInt(commandAndParameters[1]);
        if (lastCarBought == null)
        {
            System.out.println("You didn't buy a car!");
        }
        else
        {
            carDealership.returnCar(VIN);
            System.out.println(lastCarBought.display());
            lastCarBought = null;
            System.out.println("Last car purchased returned!");
        }
    }

    /**
     * NEW TO A2:
     * Prints out all transactions (buy and return).
     */

    private void printTransactions()
    {
        for (Transaction transaction : carDealership.getAccSystem().allTransactions())
        {
            System.out.println(transaction.display());
        }

    }

    /**
     * NEW TO A2:
     * Prints out the size of the sales team using salesTeamSize(), then
     * prints out all the sales people by calling displaySalespeople().
     */

    private void printSalesTeam()
    {
        System.out.println(salesTeam.salesTeamSize());
        System.out.println("They are:");
        System.out.print(salesTeam.displaySalespeople());
    }

    /**NEW TO A2:
     * Checks through the list of transactions and finds those associated with the month passed as a parameter.
     * If the months are equal, all transactions for that month are printed.
     * Note month - 1; this is to ensure that the month value passed is equal to the displayed month.
     * @param month
     */

    private void monthsTransactions(int month)
    {
        int m = month - 1;
        for (Transaction transaction : carDealership.getAccSystem().allTransactions())
        {
            int valueMonth = transaction.getDate().get(Calendar.MONTH);
            {
                if (m == valueMonth)
                {
                    System.out.println(transaction.display());
                }

            }
        }
    }

    private void printTopSalesperson()
    {
        Map<String, Integer> salesPeopleToNumCarsSold = new TreeMap<>();
        for (Transaction transaction: carDealership.getAccSystem().allTransactions())
        {
            Integer numCarsSold = salesPeopleToNumCarsSold.get(transaction.getSalesPerson());
            if(numCarsSold == null)
            {
                salesPeopleToNumCarsSold.put(transaction.getSalesPerson(), 0);
                numCarsSold = 0;
            }
            salesPeopleToNumCarsSold.put(transaction.getSalesPerson(), ++numCarsSold);
        }
        Map.Entry<String, Integer> best = ((TreeMap<String, Integer>) salesPeopleToNumCarsSold).firstEntry();
        System.out.println("Best salesperson is " + best.getKey() + " with " + best.getValue() + " sales");
    }

    /**
     * NEW TO A2:
     * Three variables created and initialized at the start.
     * boughtCars and returnedCars are increased by one when
     * their respective transaction types are matched with those of any valid transaction object.
     * If no purchases were recorded for a certain month, (month, 0) is passed to the map.
     * Also, when the command "buy" is detected, price is updated accordingly,
     * using the getPrice method.
     * Map.entry is used to simplify the task of obtaining the month with the most purchases -
     * with Map.entry, a getValue method is available to quickly determine the correct key (month), and
     * its corresponding value (total purchases) can also be obtained as easily.
     */

    private void printStats()
    {
        double price = 0;
        int boughtCars = 0;
        int returnedCars = 0;
        Map<Integer, Integer> carSalesPerMonth = new TreeMap<>();
        for (Transaction transaction : carDealership.getAccSystem().allTransactions())
        {
            int month = transaction.getDate().get(Calendar.MONTH);
            if ("buy".equalsIgnoreCase(transaction.getTransType()))
            {
                Integer carsBoughtInMonth = carSalesPerMonth.get(month);
                boughtCars++;
                if(carsBoughtInMonth == null)
                {
                    carSalesPerMonth.put(month, 0);
                    carsBoughtInMonth = 0;
                }
                //carsBoughtInMonth + 1... to match up with displayed month
                carSalesPerMonth.put(month, carsBoughtInMonth+1);
                price += transaction.getPrice();
            }
            if ("ret".equalsIgnoreCase(transaction.getTransType()))
            {
                returnedCars++;
            }
        }
        System.out.println("Total sales : $" + price);
        System.out.println("Average sale value (year, $): " + price/12);
        double salesPerMonth = 0;
        // Initialized to value that cannot be a month value
        int maxSalesMonth = -1;
        for(Map.Entry<Integer, Integer> entry: carSalesPerMonth.entrySet())
        {
            if(salesPerMonth < entry.getValue())
            {
                salesPerMonth = entry.getValue();
                maxSalesMonth  = entry.getKey();
            }
        }
        System.out.println("Month with most cars bought: " + (maxSalesMonth + 1)  + " with: " + salesPerMonth);
        System.out.println("# of cars bought: " + boughtCars);
        System.out.println("# of cars returned: " + returnedCars);
    }

    /**
     * Calls carDealership's sortByPrice method.
     */

    private void sortedPriceCommand()
    {
        System.out.println("Sorted by price!");
        carDealership.sortByPrice();
    }

    /**
     * Calls carDealership's sortBySafetyRating method.
     */

    private void sortedSafetyCommand()
    {
        System.out.println("Sorted by safety rating!");
        carDealership.sortBySafetyRating();
    }

    /**
     * Calls carDealership's sortByMaxRange method.
     */

    private void sortedRangeCommand()
    {
        System.out.println("Sorted by max range!");
        carDealership.sortByMaxRange();
    }

    /**
     * Checks if second and third input strings are valid inputs.
     * Passes these to carDealership's filterByPrice() as the minimum limit and maximum limit for price, respectively.
     * Catches exception for invalid inputs.
     */

    private void filterPriceCommand(String[] commandAndParameters)
    {
        try
        {
            double minPrice = Double.parseDouble(commandAndParameters[1]);
            double maxPrice = Double.parseDouble(commandAndParameters[2]);
            System.out.println("Price filtered!");
            carDealership.filterByPrice(minPrice, maxPrice);
        }
        catch(Exception e)
        {
            System.out.println("Error! Syntax of fpr command is [fpr] [minimum price] [maximum price]");
        }
    }

    /**
     * Calls carDealership's filterByElectric method.
     */

    private void filterElectricCommand()
    {
        System.out.println("Electric motors filtered!");
        carDealership.filterByElectric();
    }

    /**
     * Calls carDealership's filterByAWD method.
     */

    private void filterAWDCommand()
    {
        System.out.println("AWD filtered!");
        carDealership.filterByAWD();
    }

    /**
     * Calls carDealership's FiltersClear method.
     */

    private void filterClearCommand()
    {
        System.out.println("All filters cleared!");
        carDealership.FiltersClear();
    }

    /**
     * Prints available commands.
     * NEW TO A2: New commands described below.
     */

    private void showAvailableCommands()
    {
        System.out.println("Available commands are (NOTE - not case sensitive):");
        System.out.println("L - List cars; Q - Quit; Buy - buy a car (buy + VIN)");
        System.out.println("Ret - Return last car that was bought (ret + VIN); ADD - adds cars (here, cars.txt)");
        System.out.println("SPR - sorts by price; SSR - sorts by safety rating; SMR - sorts by max range");
        System.out.println("FPR - filters by price; FEL - filters electric motors");
        System.out.println("FAW - filters by AWD; FCL - clears all filters");
        System.out.println("-----------NEW-----------");
        System.out.println("SALES - Prints all bought cars; SALES TEAM - print the sales team's names ");
        System.out.println("SALES TOPSP - prints the highest-selling salesperson");
        System.out.println("SALES m - prints all sales for the month m");
        System.out.println("SALES STATS - prints total sales ($), average sales over the year ($),");
        System.out.println("the number of bought and returned cars, and the month with the best sales ($)");
    }

    /**
     * This is where cars.txt is read and passed into an array list (newArr).
     * Uses LineNumberReader and Scanner to read from the file.
     * Exception thrown here caught in simulator class's addCommand method.
     * Note: In the while loop, the order of the line scanned variables does not align with the order
     * they are passed into the car object. This is due to their ordering in cars.txt.
     * NEW TO A2: This method has been moved to this class from CarDealership.
     * No other changes.
     * @return array list of cars from cars.txt
     * @throws IOException
     */

    public static ArrayList<Car> loadFromFile() throws IOException
    {
        ArrayList<Car> newArr = new ArrayList<>();
        File file = new File("cars.txt");
        if (!file.exists())
        {
            throw new IOException("CarList could not be found!");
        }
        else
        {
            LineNumberReader reader = new LineNumberReader(new FileReader(file));
            while(true)
            {
                String line = reader.readLine();
                if(line == null)
                {
                    break;
                }
                //Manufacturer,color,model, power,safety rating,max range, AWD, number of wheels (from awd), price
                Scanner lineScanner = new Scanner(line);
                String mfg = lineScanner.next();
                String colour = lineScanner.next();
                String modelString = lineScanner.next();
                Car.Model model = Car.Model.valueOf(modelString);
                String powerString = lineScanner.next();
                int power = powerString.equalsIgnoreCase("GAS_ENGINE") ? Vehicle.GAS_ENGINE: Vehicle.ELECTRIC_MOTOR;
                double safetyRating = lineScanner.nextDouble();
                int maxRange = lineScanner.nextInt();
                String wheelDrivesString = lineScanner.next();
                boolean awd = wheelDrivesString.equalsIgnoreCase("AWD") ? true: false;
                int numWheels = wheelDrivesString.equalsIgnoreCase("AWD") ? 4: 2;
                double price = lineScanner.nextDouble();
                int rechargeTime;
                //Catches exception thrown for empty cells in rechargeTime column
                try
                {
                    rechargeTime = lineScanner.nextInt();
                }
                catch (NoSuchElementException e)
                {
                    //The car is NOT electric, as there is no value in the rechargeTime column.
                    rechargeTime = 0;
                }
                String batteryType = "Lithium";
                Car car;
                if(rechargeTime == 0)
                {
                    car = new Car(mfg, colour, power, numWheels, model, maxRange, safetyRating,
                            price, awd);
                }
                else
                {
                    car = new ElectricCar(mfg, colour, power, numWheels, model, maxRange, safetyRating,
                            price, awd, batteryType, rechargeTime);
                }
                newArr.add(car);
            }
        }
        return newArr;
    }
}

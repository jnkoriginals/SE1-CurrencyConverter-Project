package de.hdm_stuttgart.mi.sd1project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * A simple http://logging.apache.org/log4j/2.x demo,
 * see file src/main/resources/log4j2.xml for configuration options
 * and A1.log containing debugging output.
 */
public class App {
    static private final Logger log = LogManager.getLogger(App.class);

    /**
     * Your application's main entry point.
     *
     * @param args Yet unused
     */
    public static void main(String[] args) throws IOException {
        InterfaceClass interfaceClass = new InterfaceClass();
        interfaceClass.showInterface();

    }
}

class InterfaceClass {

    private final CurrencyService currencyService;

    public InterfaceClass() {
        currencyService = new CurrencyService();
    }

    public void showInterface() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        ApiResponse currencyDataResponse = currencyService.getData();

        //to test currencyConverter:
        Boolean returnfullList = false;
        String baseCurrency1 = "RUB";
        Double baseCurrencyAmount1 = 15.00;
        String targetCurrency1 = "EUR";
        currencyService.currencyConverter(returnfullList,baseCurrency1,baseCurrencyAmount1,targetCurrency1);
        System.out.println("\n| "+currencyService.convertedResults);
        //


        Map<String, String> supportedCodes = currencyDataResponse.getSupportedCurrencies();

        System.out.println("\n+++++++++++++++++++++");
        System.out.println("  Currency Converter ");
        System.out.println("+++++++++++++++++++++");

        while (!exit) {
            System.out.println("\nAvailable Options:");
            System.out.println("[1] Show available currencies.");
            System.out.println("[2] Convert.");
            System.out.println("[3] Print out to PDF.");
            System.out.println("[0] Exit.\n");

            String option = scanner.next();

            switch (option) {
                case "1" -> {
                    System.out.println("Available Currencies:");
                    for (String key : supportedCodes.keySet()) {
                        System.out.println(key + " - " + supportedCodes.get(key));
                    }
                }
                case "2" -> {
                    String baseCurrency = "";
                    while (!supportedCodes.containsKey(baseCurrency)) {
                        System.out.println("\nEnter base currency code:");
                        baseCurrency = scanner.next().toUpperCase();

                        if (!supportedCodes.containsKey(baseCurrency)) {
                            System.out.println("Currency code incorrect. Check availability or typing.");
                        }
                    }
                    System.out.println(baseCurrency);
                    // TODO: set base currency;

                    String targetCurrency = "";
                    while (!supportedCodes.containsKey(targetCurrency) && !targetCurrency.equals("ALL")) {
                        System.out.println("\nEnter target currency code (type 'all' to convert to all currencies):");
                        targetCurrency = scanner.next().toUpperCase();

                        if (!supportedCodes.containsKey(targetCurrency) && !targetCurrency.equals("ALL")) {
                            System.out.println("Currency code incorrect. Check availability or typing.");
                        }
                    }
                    System.out.println(targetCurrency);
                    // TODO: set target currency;

                    System.out.println("\nEnter amount to convert:");
                    float amount = 0;
                    String amountInput = scanner.next().replace(",", ".");
                    try {
                        amount = Float.parseFloat(amountInput);
                        // TODO: set amount to convert
                    } catch (Exception e) {
                        System.out.println("Amount is not a valid number. Please try again.");
                    }
                    // convert to target currency.
                    if (!targetCurrency.equals("ALL")) {
                        // convert to target currency.
                        System.out.println(amount);
                        // TODO: Convert to target currency
                    } else {
                        // Convert to all currencies.
                        System.out.println(amount);
                        // TODO: Convert to all currencies
                        System.out.println("Converting to all currencies");
                    }
                }
                case "3" -> {
                    // TODO: PDF Export
                    System.out.println("Not yet implemented..");
                }
                case "0" -> {
                    System.out.println("Good Bye!");
                    exit = true;
                }
                default -> System.out.println("No valid option. Please enter a valid option:");
            }
        }
        scanner.close();
    }
}

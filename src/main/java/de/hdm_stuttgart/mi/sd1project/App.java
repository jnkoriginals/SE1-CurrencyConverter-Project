package de.hdm_stuttgart.mi.sd1project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * A simple http://logging.apache.org/log4j/2.x demo,
 * see file src/main/resources/log4j2.xml for configuration options
 * and A1.log containing debugging output.
 */
public class App {
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

    public void showInterface() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        ApiResponse currencyDataResponse = currencyService.getData();

        Map<String, String> supportedCodes = currencyDataResponse.getSupportedCurrencies();

        System.out.println("\n+++++++++++++++++++++");
        System.out.println("  Currency Converter ");
        System.out.println("+++++++++++++++++++++");

        String baseCurrency = "";
        Double baseCurrencyAmount = null;
        String targetCurrency = "";

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
                    baseCurrency = "";
                    baseCurrencyAmount = null;
                    targetCurrency = "";
                    currencyService.convertedResults.clear();

                    while (!supportedCodes.containsKey(baseCurrency)) {
                        System.out.println("\nEnter base currency code:");
                        baseCurrency = scanner.next().toUpperCase();

                        if (!supportedCodes.containsKey(baseCurrency)) {
                            System.out.println("Currency code incorrect. Check availability or typing.");
                        }
                    }
                    System.out.println("Base currency code set to: " + baseCurrency);
                    while (!supportedCodes.containsKey(targetCurrency) && !targetCurrency.equals("ALL")) {
                        System.out.println("\nEnter target currency code (type 'all' to convert to all currencies):");
                        targetCurrency = scanner.next().toUpperCase();

                        if (!supportedCodes.containsKey(targetCurrency) && !targetCurrency.equals("ALL")) {
                            System.out.println("Currency code incorrect. Check availability or typing.");
                        }
                    }
                    System.out.println("Target currency code set to: " + targetCurrency);

                    System.out.println("\nEnter amount to convert:");
                    float amount = 0;
                    String amountInput = scanner.next().replace(",", ".");
                    try {
                        amount = Float.parseFloat(amountInput);
                        baseCurrencyAmount = (double) amount;
                    } catch (Exception e) {
                        System.out.println("Amount is not a valid number. Please try again.");
                    }

                    if (!targetCurrency.equals("ALL")) {
                        // convert to target currency.
                        currencyService.currencyConverter(false,baseCurrency,baseCurrencyAmount,targetCurrency);
                        System.out.println(amount + " " + baseCurrency + " are:");
                        for (String key : currencyService.convertedResults.keySet()) {
                            System.out.println(currencyService.convertedResults.get(key) + " " +  key);
                        }
                    } else {
                        // Convert to all currencies.
                        currencyService.currencyConverter(true,baseCurrency,baseCurrencyAmount,targetCurrency);
                        System.out.println(amount + " " + baseCurrency + " are:");
                        for (String key : currencyService.convertedResults.keySet()) {
                            System.out.println(currencyService.convertedResults.get(key) + " " +  key);
                        }
                    }
                }
                case "3" -> {
                    if (baseCurrency != null && baseCurrencyAmount != null) {
                        PDFConverter pdfConverter = new PDFConverter();
                        pdfConverter.ConvertToPDF(baseCurrency, baseCurrencyAmount, currencyService.convertedResults);
                    } else {
                        System.out.println("Please convert before export.");
                    }
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

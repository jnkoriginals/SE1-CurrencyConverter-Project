package de.hdm_stuttgart.mi.sd1project;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


public class ApiDecoderClass {

    /**
     * Sends requests to exchangerates-api.com to retrieve conversion rates and supported currencies,
     * decodes the JSON responses, and processes the data.
     *
     * @throws IOException if an I/O error occurs during HTTP request/response handling.
     */
    public static void ApiDecoder() throws IOException {
        HttpClient httpClient = HttpClient.newHttpClient();

        // API endpoints for conversion rates and supported currencies.
        final String API_CONVERSION_RATES_REF = "https://v6.exchangerate-api.com/v6/9d1391b083edb768c5bf0fa0/latest/USD";
        final String API_SUPPORTER_CURRENCY_REF = "https://v6.exchangerate-api.com/v6/9d1391b083edb768c5bf0fa0/codes";

        HttpRequest httpConversionRatesRequest = HttpRequest.newBuilder().uri(URI.create(API_CONVERSION_RATES_REF)).build();
        HttpRequest httpSupportedCurrenciesRequest = HttpRequest.newBuilder().uri(URI.create(API_SUPPORTER_CURRENCY_REF)).build();

        Gson gson = new Gson();
        try {
            // Sending HTTP requests and receiving JSON responses.
            HttpResponse<String> httpConversionRatesResponse = httpClient.send(httpConversionRatesRequest, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> httpSupportedCurResponse = httpClient.send(httpSupportedCurrenciesRequest, HttpResponse.BodyHandlers.ofString());

            // Extracting JSON strings from the responses.
            String conversionRatesJson = httpConversionRatesResponse.body();
            String supportedCurJson = httpSupportedCurResponse.body();

            // Deserializing JSON responses into ApiResponse objects.
            ApiResponse apiResponse = gson.fromJson(conversionRatesJson, ApiResponse.class);
            ApiResponse supportedResponse = gson.fromJson(supportedCurJson, ApiResponse.class);

            Map<String, Double> conversionRates = apiResponse.getConversionRates();
            Map<String, String> supportedCodes = supportedResponse.getSupportedCurrencies();

            System.out.println(conversionRates);
            System.out.println(conversionRates.get("EUR"));
            System.out.println(supportedCodes);


            System.out.println("Supported Currencies: ");
            for (String key : supportedCodes.keySet()) {
                System.out.println(key);
            }

        } catch (Exception error) {
            System.out.println("Error: " + error);
        }

    }


}

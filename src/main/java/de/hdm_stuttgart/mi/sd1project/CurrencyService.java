package de.hdm_stuttgart.mi.sd1project;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class CurrencyService {



    /**
     * Sends requests to exchangerates-api.com to retrieve conversion rates and supported currencies,
     * decodes the JSON responses, and processes the data.
     *
     */
    public ApiResponse getData() {
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

            ApiResponse apiResponse = new ApiResponse();

            // Deserializing JSON responses into ApiResponse objects.
            ApiResponse conversionResponse = gson.fromJson(conversionRatesJson, ApiResponse.class);
            ApiResponse supportedResponse = gson.fromJson(supportedCurJson, ApiResponse.class);

            Map<String, Double> conversionRates = conversionResponse.getConversionRates();
            apiResponse.setConversionRates(conversionRates);

            Map<String, String> supportedCodes = supportedResponse.getSupportedCurrencies();
            apiResponse.setSupportedCurrencies(supportedCodes);

            return apiResponse;
        } catch (Exception error) {
            System.out.println("Error: " + error);
            return null;
        }
    }
}
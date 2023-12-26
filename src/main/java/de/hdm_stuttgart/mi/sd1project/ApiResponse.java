package de.hdm_stuttgart.mi.sd1project;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * The ApiResponse class represents the response received from the exchangerates-api.com.
 * It contains information about the result status, conversion rates, and supported currencies.
 */
public class ApiResponse {

    /**
     * The result status of the API response.
     */
    @SerializedName("result")
    private String result;

    /**
     * The conversion rates for different currencies.
     */
    @SerializedName("conversion_rates")
    private Map<String, Double> conversionRates;

    /**
     * The supported currencies with their corresponding codes.
     */
    @SerializedName("supported_codes")
    private Map<String, String> supportedCurrency;

    /**
     * Gets the result status of the API response.
     *
     * @return The result status as a String.
     */
    public String getResult() {
        return result;
    }

    /**
     * Gets the conversion rates for different currencies.
     *
     * @return A map containing currency codes as keys and their conversion rates as values.
     */
    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    /**
     * Gets the supported currencies with their corresponding codes.
     *
     * @return A map containing currency codes as keys and currency names as values.
     */
    public Map<String, String> getSupportedCurrencies() {return supportedCurrency; }
}

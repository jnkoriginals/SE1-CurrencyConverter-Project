package de.hdm_stuttgart.mi.sd1project;

import static org.junit.Assert.*;
import org.junit.Test;


public class AppTest {

    /**
     * Test data fetching.
     */
    @Test
    public void testDataFetching() {
        CurrencyService currencyService = new CurrencyService();
        ApiResponse apiResponse = currencyService.getData();

        assertNotNull(apiResponse);
        assertNotNull(apiResponse.getConversionRates());
        assertNotNull(apiResponse.getSupportedCurrencies());
    }
}

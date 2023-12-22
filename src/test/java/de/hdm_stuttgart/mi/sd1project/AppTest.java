package de.hdm_stuttgart.mi.sd1project;

import org.junit.Assert;
import org.junit.Test;

/**
 * Sample unit test of class {@link de.hdm_stuttgart.mi.sd1project.App}.
 */
public class AppTest {
    /**
     * Test for correct sum of two arguments.
     */
    @Test
    public void testApp() {
        Assert.assertEquals( 33, App.add(30, 3));
    }
}

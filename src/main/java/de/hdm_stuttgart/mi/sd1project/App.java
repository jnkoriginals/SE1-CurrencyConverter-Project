package de.hdm_stuttgart.mi.sd1project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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
    public static void main( String[] args ) throws IOException {


        ApiDecoderClass.ApiDecoder();

        /* System.out.println("""
         Hi there, let's have
         fun learning Java!""");

        log.debug("You may configure 'src/main/resources/log4j2.xml' ");
        log.debug("for adapting both console and 'A1.log' file output"); */
    }

    /**
     * This method purely exists for providing Junit tests.
     *
     * @param a first parameter
     * @param b second parameter
     * @return the sum of both parameters.
     */
    public static int add(final int a, final int b) {
        return a + b;
    }
}

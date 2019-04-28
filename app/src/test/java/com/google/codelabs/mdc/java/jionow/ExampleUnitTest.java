package com.google.codelabs.mdc.java.jionow;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void event_modelwork() {
        long millis=System.currentTimeMillis();
        Date now =new Date(millis);
        Events testevent = new Events("test", "testdescription", now, now, "testhost");
        assertEquals(now, testevent.getStartDate());
        assertEquals(now, testevent.getEndDate());
        assertEquals("test", testevent.getName());
        assertEquals("testhost", testevent.getHost());
        assertEquals("testdescription", testevent.getDescription());
        assertEquals("Past Event", testevent.status());
        assertEquals(false, testevent.isEventsHost("fakehost"));
        assertEquals(true, testevent.isEventsHost("testhost"));

    }

<<<<<<< HEAD
    @Test
    public void payments_modelwork() {
        Map<String, Object> Owed = new HashMap<>();
        Owed.put("testuser","23");
        Owed.put("testuser2", "2.5");
        Payments testpayment = new Payments("testname", "testhost", Owed);
        assertEquals("testname", testpayment.getName());
        assertEquals("testhost", testpayment.getHost());
        assertEquals("25.5", testpayment.getHostPayment());
        assertEquals("23", testpayment.getPayment("testuser"));
        assertEquals("2.5", testpayment.getPayment("testuser2"));
        assertEquals(Owed, testpayment.getOwed());
=======
    @Test
    public void payments_modelwork() {
        Map<String, Object> Owed = new HashMap<>();
        Owed.put("testuser","23");
        Owed.put("testuser2", "2.5");
        Payments testpayment = new Payments("testname", "testhost", Owed);
        assertEquals("testname", testpayment.getName());
        assertEquals("testhost", testpayment.getHost());
        assertEquals("25.5", testpayment.getHostPayment());
        assertEquals("23", testpayment.getPayment("testuser"));
        assertEquals("2.5", testpayment.getPayment("testuser2"));
        assertEquals(Owed, testpayment.getOwed());
    }

    @Test
    public void round() {
        assertEquals(String.valueOf(5.25), String.valueOf(CropGalleryImage.roundAvoid(5.252,2)));
        assertEquals(String.valueOf(3.646), String.valueOf(CropGalleryImage.roundAvoid(3.6457,3)));
>>>>>>> 1e033004180b455d016a1d2f8a75f17f3fa0b2ca
    }
}
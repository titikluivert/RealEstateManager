package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.util.Date;

import static com.openclassrooms.realestatemanager.utils.Utils.getTodayDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect()  {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void toDayDateTest()  {

        assertEquals("27/04/2020", Utils.getConvertDate(getTodayDate()));
        assertNotEquals("23/03/2020", Utils.getConvertDate(getTodayDate()));
    }


    @Test
    public void EuroToDollarsTest()  {

        assertEquals(10000, Utils.convertEuroToDollar(8120));
        assertEquals(812, Utils.convertDollarToEuro(1000));
    }

    @Test
    public void getConvertDateToUSFormatTest() {
        assertEquals("2020/03/06", Utils.getConvertDate("6/3/2020"));
        assertEquals("2020/03/23", Utils.getConvertDate("23/03/2020"));
        assertNotEquals("2020/02/23", Utils.getConvertDate("23/03/2020"));
    }


}
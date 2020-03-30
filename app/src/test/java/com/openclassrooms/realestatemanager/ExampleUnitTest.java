package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.mainUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void toDayDateTest() throws Exception {

        assertEquals("30/03/2020", mainUtils.getConvertDate(mainUtils.getTodayDate()));
        assertNotEquals("23/03/2020", mainUtils.getConvertDate(mainUtils.getTodayDate()));
    }


    @Test
    public void EuroToDollarsTest() throws Exception {

        assertEquals(10000, mainUtils.convertEuroToDollar(8120));
        assertEquals(812, mainUtils.convertDollarToEuro(1000));
    }

    @Test
    public void getConvertDateToUSFormatTest() {
        assertEquals("2020/03/06", mainUtils.getConvertDate("6/3/2020"));
        assertEquals("2020/03/23", mainUtils.getConvertDate("23/03/2020"));
        assertNotEquals("2020/02/23", mainUtils.getConvertDate("23/03/2020"));
    }

}
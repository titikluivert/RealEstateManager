package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.mainUtils;

import org.junit.Test;

import java.util.Date;

import static com.openclassrooms.realestatemanager.utils.mainUtils.getTodayDate;
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

        assertEquals("07/04/2020", mainUtils.getConvertDate(getTodayDate()));
        assertNotEquals("23/03/2020", mainUtils.getConvertDate(getTodayDate()));
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


    @Test
    public void parseDateTest() {
        Date result = mainUtils.DateConverters.fromTimestamp(Long.parseLong(getTodayDate().replace("/", "").trim()));
        Long resultLong = mainUtils.DateConverters.dateToTimestamp(result);
        String dateString = String.valueOf(resultLong);
        String finalResult = mainUtils.changeStringToDate(dateString);
        assertEquals(finalResult, mainUtils.getConvertDate(getTodayDate()));
    }

}
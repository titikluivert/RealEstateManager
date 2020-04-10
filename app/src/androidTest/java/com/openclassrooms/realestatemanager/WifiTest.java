package com.openclassrooms.realestatemanager;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.utils.mainUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WifiTest {

    @Test
    public void wifiIsOn() {
        boolean isPresent = mainUtils.isInternetAvailable(getContext());
        assertTrue(isPresent);
    }
}
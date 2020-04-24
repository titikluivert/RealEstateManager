package com.openclassrooms.realestatemanager;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.openclassrooms.realestatemanager.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class WifiTest {

    @Test
    public void wifiIsOn() {
        boolean isPresent = Utils.isInternetAvailable(getContext());
        assertTrue(isPresent);
    }
}

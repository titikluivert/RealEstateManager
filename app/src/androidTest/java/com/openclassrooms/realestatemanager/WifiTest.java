package com.openclassrooms.realestatemanager;

import android.net.wifi.WifiManager;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class WifiTest {
    private WifiManager wifiManager = (WifiManager) getContext().getSystemService(getContext().WIFI_SERVICE);

    @Test
    public void wifiIsOn() throws InterruptedException {
        wifiManager.setWifiEnabled(true);
        TimeUnit.SECONDS.sleep(10);
        assertTrue(Utils.isInternetAvailable(getContext()));
    }

    @Test
    public void wifiIsOff() throws InterruptedException {
        wifiManager.setWifiEnabled(false);
        TimeUnit.SECONDS.sleep(10);
        assertFalse(Utils.isInternetAvailable(getContext()));
    }




}

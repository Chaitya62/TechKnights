package com.lakj.comspace.simpletextclient;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;

import java.util.List;

public class VirtualWaitress extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_waitress);
        wificonnect();
        Thread startTimer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    Intent intent0 = new Intent(VirtualWaitress.this, SlimpleTextClientActivity.class);
                    startActivity(intent0);
                    overridePendingTransition(R.anim.fadin, R.anim.fadout);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        startTimer.start();
    }


    public void wificonnect()
    {

        String networkSSID = "Jsgrut";
        String networkPass = "987654321";
        WifiManager wfMgr = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        if (!wfMgr.isWifiEnabled())
            wfMgr.setWifiEnabled(true);
        WifiConfiguration wfc = new WifiConfiguration();
        wfc.SSID=networkSSID;

        List<ScanResult> networkList = ((WifiManager)getSystemService(Context.WIFI_SERVICE)).getScanResults();
        for (ScanResult network : networkList)
        {
            String Capabilities =  network.capabilities;

            //Then you could add some code to check for a specific security type.
            if(Capabilities.contains("WPA"))
            {
                wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                wfc.preSharedKey = "\"".concat(networkPass).concat("\"");
            }
            else if(Capabilities.contains("WEP"))
            {
                wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                wfc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                wfc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

                if (networkPass.matches("-?[0-9a-fA-F]+"))
                    wfc.wepKeys[0] = networkPass;
                else
                    wfc.wepKeys[0] = "\"".concat(networkPass).concat("\"");
                wfc.wepTxKeyIndex = 0;
            }
            else
            {
                wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                wfc.allowedAuthAlgorithms.clear();
                wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            }

        }


        int networkId = wfMgr.addNetwork(wfc);
        wfMgr.disconnect();
        wfMgr.enableNetwork(networkId, true);
        wfMgr.reconnect();

    }

}

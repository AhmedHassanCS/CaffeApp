package com.apps.ahfreelancing.caffetask.presentation.view.utility;

import android.content.Context;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class ConnectionUtility {
    public static boolean isNetworkConnected() {
        try {

            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            return !ipAddr.equals("");

        } catch (UnknownHostException e) {
            return false;
        }
    }

}

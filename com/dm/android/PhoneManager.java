package com.dm.android;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

/**
 * android.permission.READ_PHONE_STATE
 * */

public class PhoneManager {
	static public String getImsi(Context context) {
		TelephonyManager tpManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tpManager.getSubscriberId();
	}
	
	static public String getImei(Context context) {
		TelephonyManager tpManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tpManager.getDeviceId();
	}
	
    public static String getMacAddr(Context arg3) {
    	WifiManager wifiMgr = (WifiManager)arg3.getSystemService(Context.WIFI_SERVICE);
        String result = wifiMgr.getConnectionInfo().getMacAddress();

        return result;
    }
    
    /**
     * wifi获取 路由ip地址
     *
     * @param context
     * @return
     */
	private static String getWifiRouteIPAddress(Context context) {
        WifiManager wifi_service = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
//        WifiInfo wifiinfo = wifi_service.getConnectionInfo();
//        System.out.println("Wifi info----->" + wifiinfo.getIpAddress());
//        System.out.println("DHCP info gateway----->" + Formatter.formatIpAddress(dhcpInfo.gateway));
//        System.out.println("DHCP info netmask----->" + Formatter.formatIpAddress(dhcpInfo.netmask));
        //DhcpInfo中的ipAddress是一个int型的变量，通过Formatter将其转化为字符串IP地址
		@SuppressWarnings("deprecation")
		String routeIp = Formatter.formatIpAddress(dhcpInfo.gateway);
//        Log.d(TAG, "wifi route ip：" + routeIp);

        return routeIp;
    }
}

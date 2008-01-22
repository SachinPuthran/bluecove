/**
 *  BlueCove - Java library for Bluetooth
 *  Copyright (C) 2006-2007 Vlad Skarzhevskyy
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  @version $Id$
 */
package net.sf.bluecove;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.L2CAPConnection;
import javax.bluetooth.LocalDevice;

import junit.framework.Assert;

import net.sf.bluecove.util.BluetoothTypesInfo;
import net.sf.bluecove.util.StringUtils;

/**
 * @author vlads
 * 
 */
public class TestResponderCommon {

	public static int receiveMTU_max = L2CAPConnection.DEFAULT_MTU;

	public static void printProperty(String property) {
		String val = LocalDevice.getProperty(property);
		if (val != null) {
			Logger.info(property + ":" + val);
		}
	}

	public static void startLocalDevice() throws BluetoothStateException {

		if (!Configuration.initializeLocalDevice) {
			return;
		}

		LocalDevice localDevice = LocalDevice.getLocalDevice();
		Logger.info("address:" + localDevice.getBluetoothAddress());
		Logger.info("name:" + localDevice.getFriendlyName());
		Logger.info("class:" + BluetoothTypesInfo.toString(localDevice.getDeviceClass()));

		printProperty("bluetooth.api.version");
		printProperty("bluetooth.sd.trans.max");
		printProperty("bluetooth.sd.attr.retrievable.max");
		printProperty("bluetooth.connected.devices.max");
		printProperty("bluetooth.connected.inquiry.scan");
		printProperty("bluetooth.connected.page.scan");
		printProperty("bluetooth.connected.inquiry");
		printProperty("bluetooth.l2cap.receiveMTU.max");

		String bluecoveVersion = LocalDevice.getProperty("bluecove");
		if (StringUtils.isStringSet(bluecoveVersion)) {
			Configuration.isBlueCove = true;

			Logger.info("bluecove:" + bluecoveVersion);
			Logger.info("stack:" + LocalDevice.getProperty("bluecove.stack"));

			Assert.assertNotNull("BT Address is null", localDevice.getBluetoothAddress());
			if (!Configuration.windowsCE) {
				Assert.assertNotNull("BT Name is null", localDevice.getFriendlyName());
			}

			Logger.info("stack version:" + LocalDevice.getProperty("bluecove.stack.version"));
			Logger.info("radio manufacturer:" + LocalDevice.getProperty("bluecove.radio.manufacturer"));
			Logger.info("radio version:" + LocalDevice.getProperty("bluecove.radio.version"));

			Configuration.stackWIDCOMM = StringUtils.equalsIgnoreCase("WIDCOMM", LocalDevice
					.getProperty("bluecove.stack"));
			Configuration.supportL2CAP = Configuration.stackWIDCOMM || Configuration.macOSx || Configuration.linux;
		}

		String receiveMTUstr = LocalDevice.getProperty("bluetooth.l2cap.receiveMTU.max");
		if (receiveMTUstr != null) {
			int max = Integer.valueOf(receiveMTUstr).intValue();
			if (max < receiveMTU_max) {
				receiveMTU_max = max;
			}
		}
	}

	public static String niceDeviceName(String bluetoothAddress) {
		String w = getWhiteDeviceName(bluetoothAddress);
		if (w == null) {
			w = (String) TestResponderClient.recentDeviceNames.get(bluetoothAddress.toUpperCase());
		}
		return (w != null) ? w : bluetoothAddress;
	}

	public static String getWhiteDeviceName(String bluetoothAddress) {
		if ((bluetoothAddress == null) || (Configuration.testDeviceNames == null)) {
			return null;
		}
		String addr = bluetoothAddress.toUpperCase();
		return (String) Configuration.testDeviceNames.get(addr);
	}

}
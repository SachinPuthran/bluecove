/**
 * BlueCove BlueZ module - Java library for Bluetooth on Linux
 *  Copyright (C) 2008 Mina Shokry
 *  Copyright (C) 2007 Vlad Skarzhevskyy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @version $Id$
 */
package com.intel.bluetooth;

import javax.bluetooth.DataElement;
import javax.bluetooth.DiscoveryListener;

/**
 * @author vlads
 * 
 * Export constants to native code
 * 
 */
abstract class BluetoothStackBlueZConsts {

	static final int INQUIRY_COMPLETED = DiscoveryListener.INQUIRY_COMPLETED;

	static final int INQUIRY_TERMINATED = DiscoveryListener.INQUIRY_TERMINATED;

	static final int INQUIRY_ERROR = DiscoveryListener.INQUIRY_ERROR;

	static final int SERVICE_SEARCH_COMPLETED = DiscoveryListener.SERVICE_SEARCH_COMPLETED;

	static final int SERVICE_SEARCH_TERMINATED = DiscoveryListener.SERVICE_SEARCH_TERMINATED;

	static final int SERVICE_SEARCH_ERROR = DiscoveryListener.SERVICE_SEARCH_ERROR;

	static final int SERVICE_SEARCH_NO_RECORDS = DiscoveryListener.SERVICE_SEARCH_NO_RECORDS;

	static final int SERVICE_SEARCH_DEVICE_NOT_REACHABLE = DiscoveryListener.SERVICE_SEARCH_DEVICE_NOT_REACHABLE;

	static final int DataElement_NULL = DataElement.NULL;

	static final int DataElement_U_INT_1 = DataElement.U_INT_1;

	static final int DataElement_U_INT_2 = DataElement.NULL;

	static final int DataElement_U_INT_4 = DataElement.U_INT_4;

	static final int DataElement_U_INT_8 = DataElement.U_INT_8;

	static final int DataElement_U_INT_16 = DataElement.U_INT_16;

	static final int DataElement_INT_1 = DataElement.INT_1;

	static final int DataElement_INT_2 = DataElement.INT_2;

	static final int DataElement_INT_4 = DataElement.INT_4;

	static final int DataElement_INT_8 = DataElement.INT_8;

	static final int DataElement_INT_16 = DataElement.INT_16;

	static final int DataElement_URL = DataElement.URL;

	static final int DataElement_UUID = DataElement.UUID;

	static final int DataElement_BOOL = DataElement.BOOL;

	static final int DataElement_STRING = DataElement.STRING;

	static final int DataElement_DATSEQ = DataElement.DATSEQ;

	static final int DataElement_DATALT = DataElement.DATALT;
}
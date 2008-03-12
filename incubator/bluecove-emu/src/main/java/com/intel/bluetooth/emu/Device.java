/**
 *  BlueCove - Java library for Bluetooth
 *  Copyright (C) 2008 Michael Lifshits
 *  Copyright (C) 2008 Vlad Skarzhevskyy
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
package com.intel.bluetooth.emu;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author vlads
 * 
 */
class Device {

	private DeviceDescriptor descriptor;

	private DeviceSDP sdp;

	private Hashtable servicesOpen = new Hashtable();

	private Vector serviceListeners;

	private Object serviceNotification = new Object();

	private Hashtable connections = new Hashtable();

	Device(DeviceDescriptor descriptor) {
		this.descriptor = descriptor;
		this.serviceListeners = new Vector();
	}

	DeviceDescriptor getDescriptor() {
		return this.descriptor;
	}

	synchronized DeviceSDP getDeviceSDP(boolean create) {
		if (create && (sdp == null)) {
			sdp = new DeviceSDP(descriptor.getAddress());
		}
		return sdp;
	}

	void openService(String portID) {
		servicesOpen.put(portID, portID);
	}

	void closeService(String portID) {
		servicesOpen.remove(portID);
		ServiceListener sl;
		while ((sl = removeServiceListener(portID)) != null) {
			sl.close();
		}
	}

	ServiceListener createServiceListener(String portID) {
		ServiceListener sl = new ServiceListener(portID);
		synchronized (serviceListeners) {
			serviceListeners.addElement(sl);
		}
		return sl;
	}

	private ServiceListener removeServiceListener(String portID) {
		ServiceListener sl = null;
		synchronized (serviceListeners) {
			for (Enumeration iterator = serviceListeners.elements(); iterator.hasMoreElements();) {
				ServiceListener s = (ServiceListener) iterator.nextElement();
				if (s.getPortID().equals(portID)) {
					serviceListeners.removeElement(s);
					sl = s;
					break;
				}
			}
		}
		return sl;
	}

	void serviceListenerAccepting(String portID) {
		synchronized (serviceNotification) {
			serviceNotification.notifyAll();
		}
	}

	ServiceListener connectService(String portID, long timeout) throws IOException {
		if (servicesOpen.get(portID) == null) {
			return null;
		}
		ServiceListener sl = removeServiceListener(portID);
		long endOfDellay = System.currentTimeMillis() + timeout;
		while ((sl == null) && (timeout > 0)) {
			long timeleft = endOfDellay - System.currentTimeMillis();
			if (timeleft <= 0) {
				throw new IOException("Service " + portID + " not accepting");
			}
			try {
				synchronized (serviceNotification) {
					serviceNotification.wait(timeleft);
				}
			} catch (InterruptedException e) {
				break;
			}
			if (servicesOpen.get(portID) == null) {
				break;
			}
			sl = removeServiceListener(portID);
		}
		return sl;
	}

	void addConnectionBuffer(long connectionId, ConnectionBuffer c) {
		connections.put(new Long(connectionId), c);
	}

	ConnectionBuffer getConnectionBuffer(long connectionId) {
		return (ConnectionBuffer) connections.get(new Long(connectionId));
	}

	void closeConnection(long connectionId) throws IOException {
		ConnectionBuffer c = getConnectionBuffer(connectionId);
		if (c == null) {
			throw new IOException("No such connection " + connectionId);
		}
		c.close();
	}

	void release() {
		servicesOpen.clear();
		for (Enumeration iterator = serviceListeners.elements(); iterator.hasMoreElements();) {
			ServiceListener s = (ServiceListener) iterator.nextElement();
			s.close();
		}
		serviceListeners.clear();
		for (Enumeration iterator = connections.elements(); iterator.hasMoreElements();) {
			ConnectionBuffer c = (ConnectionBuffer) iterator.nextElement();
			try {
				c.close();
			} catch (IOException e) {
			}
		}
		connections.clear();
	}
}

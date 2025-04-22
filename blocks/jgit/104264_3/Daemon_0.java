
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.InetSocketAddress;

import org.junit.Test;

public class DaemonTest {

	@Test
	public void testDaemonStop() throws Exception {
		Daemon d = new Daemon();
		d.start();
		InetSocketAddress address = d.getAddress();
		assertTrue("Port should be allocated"
		assertTrue("Daemon should be running"
		d.stopAndWait();
		d = new Daemon(address);
		d.start();
		InetSocketAddress newAddress = d.getAddress();
		assertEquals("New daemon should run on the same port"
				newAddress);
		assertTrue("Daemon should be running"
		Thread.sleep(1000);
		d.stopAndWait();
	}

	@Test
	public void testDaemonRestart() throws Exception {
		Daemon d = new Daemon();
		d.start();
		InetSocketAddress address = d.getAddress();
		assertTrue("Port should be allocated"
		assertTrue("Daemon should be running"
		Thread.sleep(1000);
		d.stopAndWait();
		d.start();
		InetSocketAddress newAddress = d.getAddress();
		assertEquals("Daemon should again run on the same port"
				newAddress);
		assertTrue("Daemon should be running"
		Thread.sleep(1000);
		d.stopAndWait();
	}
}

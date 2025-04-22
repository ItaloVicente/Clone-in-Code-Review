
package org.eclipse.jgit.storage.dht;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.lib.Config;
import org.junit.Test;

public class TimeoutTest {
	@Test
	public void testGetTimeout() {
		Timeout def = Timeout.seconds(2);
		Config cfg = new Config();
		Timeout t;

		cfg.setString("core"
		t = Timeout.getTimeout(cfg
		assertEquals(500
		assertEquals(TimeUnit.MILLISECONDS

		cfg.setString("core"
		t = Timeout.getTimeout(cfg
		assertEquals(5200
		assertEquals(TimeUnit.MILLISECONDS

		cfg.setString("core"
		t = Timeout.getTimeout(cfg
		assertEquals(60
		assertEquals(TimeUnit.SECONDS
	}
}


package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.http.server.ClientVersionUtil.invalidVersion;
import static org.eclipse.jgit.http.server.ClientVersionUtil.parseVersion;

import org.junit.Assert;
import org.junit.Test;

public class ClientVersionUtilTest {
	@Test
	public void testParse() {
		assertEquals("1.6.5"
		assertEquals("1.6.6"
		assertEquals("1.7.5"
		assertEquals("1.7.6.1"

		assertEquals("1.5.4.3"
		assertEquals("1.7.0.2"
		assertEquals("1.7.10.2"

		assertEquals(ClientVersionUtil.toString(invalidVersion())
	}

	private static void assertEquals(String exp
		Assert.assertEquals(exp
	}
}

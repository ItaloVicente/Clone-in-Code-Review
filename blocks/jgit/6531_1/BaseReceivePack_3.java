
package org.eclipse.jgit.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.eclipse.jgit.http.server.ClientVersionUtil.hasPushStatusBug;
import static org.eclipse.jgit.http.server.ClientVersionUtil.invalidVersion;
import static org.eclipse.jgit.http.server.ClientVersionUtil.parse;

import org.junit.Assert;
import org.junit.Test;

public class ClientVersionUtilTest {
	@Test
	public void testParse() {
		assertEquals("1.6"
		assertEquals("1.6.6"
		assertEquals("1.7.5"
		assertEquals("1.7.6.1"

		assertEquals("1.5.4.3"
		assertEquals("1.7.0.2"
		assertEquals("1.7.10.2"

		assertEquals(ClientVersionUtil.toString(invalidVersion())
	}

	@Test
	public void testPushStatusBug() {
		assertTrue(hasPushStatusBug(parse("git/1.6.6")));
		assertTrue(hasPushStatusBug(parse("git/1.6.6.1")));
		assertTrue(hasPushStatusBug(parse("git/1.7.9")));

		assertFalse(hasPushStatusBug(parse("git/1.7.8.6")));
		assertFalse(hasPushStatusBug(parse("git/1.7.9.1")));
		assertFalse(hasPushStatusBug(parse("git/1.7.9.2")));
		assertFalse(hasPushStatusBug(parse("git/1.7.10")));
	}

	private static void assertEquals(String exp
		Assert.assertEquals(exp
	}
}

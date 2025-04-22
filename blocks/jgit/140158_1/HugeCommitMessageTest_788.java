package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.junit.Test;

public class GpgConfigTest {

	private static Config parse(String content) throws ConfigInvalidException {
		final Config c = new Config(null);
		c.fromText(content);
		return c;
	}

	@Test
	public void isSignCommits_defaultIsFalse() throws Exception {
		Config c = parse("");

		assertFalse(new GpgConfig(c).isSignCommits());
	}

	@Test
	public void isSignCommits_false() throws Exception {
		);

		assertFalse(new GpgConfig(c).isSignCommits());
	}

	@Test
	public void isSignCommits_true() throws Exception {
		);

		assertTrue(new GpgConfig(c).isSignCommits());
	}

	@Test
	public void testGetKeyFormat_defaultsToOpenpgp() throws Exception {
		Config c = parse("");

		assertEquals(GpgConfig.GpgFormat.OPENPGP
				new GpgConfig(c).getKeyFormat());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetKeyFormat_failsForInvalidValue() throws Exception {
		);

		new GpgConfig(c).getKeyFormat();
		fail("Call should not have succeeded!");
	}

	@Test
	public void testGetKeyFormat_openpgp() throws Exception {
		);

		assertEquals(GpgConfig.GpgFormat.OPENPGP
				new GpgConfig(c).getKeyFormat());
	}

	@Test
	public void testGetKeyFormat_x509() throws Exception {
		);

		assertEquals(GpgConfig.GpgFormat.X509
	}

	@Test
	public void testGetSigningKey() throws Exception {
		);

		assertEquals("0x2345"
	}

	@Test
	public void testGetSigningKey_defaultToNull() throws Exception {
		Config c = parse("");

		assertNull(new GpgConfig(c).getSigningKey());
	}
}

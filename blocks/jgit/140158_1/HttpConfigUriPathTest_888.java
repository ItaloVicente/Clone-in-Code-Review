
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.Config;
import org.junit.Before;
import org.junit.Test;

public class HttpConfigTest {

	private static final String DEFAULT = "[http]\n" + "\tpostBuffer = 1\n"
			+ "\tsslVerify= true\n" + "\tfollowRedirects = true\n"
			+ "\tmaxRedirects = 5\n\n";

	private Config config;

	@Before
	public void setUp() {
		config = new Config();
	}

	@Test
	public void testDefault() throws Exception {
		HttpConfig http = new HttpConfig(config
		assertEquals(1024 * 1024
		assertTrue(http.isSslVerify());
		assertEquals(HttpConfig.HttpRedirectMode.INITIAL
				http.getFollowRedirects());
	}

	@Test
	public void testMatchSuccess() throws Exception {
				+ "\tpostBuffer = 1024\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1024
		http = new HttpConfig(config
		assertEquals(1
		http = new HttpConfig(config
		assertEquals(1
		http = new HttpConfig(config
		assertEquals(1024
		http = new HttpConfig(config
		assertEquals(1
	}

	@Test
	public void testMatchWithOnlySchemeInConfig() throws Exception {
		config.fromText(
		HttpConfig http = new HttpConfig(config
		assertEquals(1
	}

	@Test
	public void testMatchWithPrefixUriInConfig() throws Exception {
				+ "\tpostBuffer = 1024\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1
	}

	@Test
	public void testMatchCaseSensitivity() throws Exception {
				+ "\tpostBuffer = 1024\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1024
	}

	@Test
	public void testMatchWithInvalidUriInConfig() throws Exception {
		config.fromText(
		HttpConfig http = new HttpConfig(config
		assertEquals(1
	}

	@Test
	public void testMatchWithInvalidAndValidUriInConfig() throws Exception {
		HttpConfig http = new HttpConfig(config
		assertEquals(2048
	}

	@Test
	public void testMatchWithHostEndingInSlash() throws Exception {
				+ "\tpostBuffer = 1024\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1024
	}

	@Test
	public void testMatchWithUser() throws Exception {
				+ "\tpostBuffer = 1024\n"
				+ "\tpostBuffer = 2048\n"
				+ "\tpostBuffer = 4096\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1024
		http = new HttpConfig(config
		assertEquals(4096
		http = new HttpConfig(config
		assertEquals(2048
		http = new HttpConfig(config
		assertEquals(4096
		http = new HttpConfig(config
		assertEquals(1024
		http = new HttpConfig(config
		assertEquals(2048
		http = new HttpConfig(config
		assertEquals(1024
	}

	@Test
	public void testMatchLonger() throws Exception {
				+ "\tpostBuffer = 1024\n"
				+ "\tpostBuffer = 2048\n");
		HttpConfig http = new HttpConfig(config
		assertEquals(1024
		http = new HttpConfig(config
		assertEquals(1
		http = new HttpConfig(config
		assertEquals(1
		http = new HttpConfig(config
		assertEquals(2048
		http = new HttpConfig(config
		assertEquals(1024
		http = new HttpConfig(config
		assertEquals(1024
	}
}


package org.eclipse.jgit.transport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServerOptionsTest {
	@Test
	public void testServerOptionLineValue() {
		ServerOptions so = new ServerOptions();
		so.add("server-option=aValue");

		assertEquals(so.getServerOptions().size()
		assertThat(so.getServerOptions()
		assertThat(so.getAgent()
	}

	@Test
	public void testServerOptionLineKeyValue() {
		ServerOptions so = new ServerOptions();
		so.add("server-option=aKey=aValue");

		assertEquals(so.getServerOptions().size()
		assertThat(so.getServerOptions()
		assertThat(so.getAgent()
	}

	@Test
	public void testMultipleServerOptionLines() {
		ServerOptions so = new ServerOptions();
		so.add("server-option=a");
		so.add("server-option=b");
		so.add("server-option=c");

		assertEquals(so.getServerOptions().size()
		assertThat(so.getServerOptions()
		assertThat(so.getAgent()
	}

	@Test
	public void testAgentLine() {
		ServerOptions so = new ServerOptions();
		so.add("agent=unit-test");

		assertEquals(so.getServerOptions().size()
		assertThat(so.getAgent()
	}

	@Test
	public void testUnknownCapabilityLine() {
		ServerOptions so = new ServerOptions();
		so.add("unknown-capability");

		assertEquals(so.getServerOptions().size()
		assertThat(so.getAgent()
	}

	@Test
	public void testEmptyServerOptionLine() {
		ServerOptions so = new ServerOptions();
		so.add("server-option=");

		assertEquals(so.getServerOptions().size()
		assertThat(so.getAgent()
	}

	@Test
	public void testServerOptionWithoutEquals() {
		ServerOptions so = new ServerOptions();
		so.add("server-option");

		assertEquals(so.getServerOptions().size()
		assertThat(so.getAgent()
	}

	@Test
	public void testNullLine() {
		ServerOptions so = new ServerOptions();
		so.add(null);

		assertEquals(so.getServerOptions().size()
		assertThat(so.getAgent()
	}

	@Test
	public void testEmptyLine() {
		ServerOptions so = new ServerOptions();
		so.add("");

		assertEquals(so.getServerOptions().size()
		assertThat(so.getAgent()
	}
}

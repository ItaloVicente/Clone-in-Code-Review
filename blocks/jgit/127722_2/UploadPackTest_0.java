
package org.eclipse.jgit.transport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandCapabilitiesTest {
	@Test
	public void testServerOptionLineValue() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability("server-option=aValue");

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getServerOptions()
		assertThat(cc.getAgent()
	}

	@Test
	public void testServerOptionLineKeyValue() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability("server-option=aKey=aValue");

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getServerOptions()
		assertThat(cc.getAgent()
	}

	@Test
	public void testMultipleServerOptionLines() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability("server-option=a");
		cc.addCmdCapability("server-option=b");
		cc.addCmdCapability("server-option=c");

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getServerOptions()
		assertThat(cc.getAgent()
	}

	@Test
	public void testAgentLine() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability("agent=unit-test");

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getAgent()
	}

	@Test
	public void testUnknownCapabilityLine() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability("unknown-capability");

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getAgent()
	}

	@Test
	public void testEmptyServerOptionLine() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability("server-option=");

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getAgent()
	}

	@Test
	public void testServerOptionWithoutEquals() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability("server-option");

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getAgent()
	}

	@Test
	public void testNullLine() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability(null);

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getAgent()
	}

	@Test
	public void testEmptyLine() {
		CommandCapabilities cc = new CommandCapabilities();
		cc.addCmdCapability("");

		assertEquals(cc.getServerOptions().size()
		assertThat(cc.getAgent()
	}
}

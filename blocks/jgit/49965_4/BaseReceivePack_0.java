
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class BaseReceivePackTest {
	@Test
	public void chomp() {
		assertEquals("foo"
		assertEquals("foo"
		assertEquals("foo\n"
	}

	@Test
	public void parseCommand() {
		String input = "0000000000000000000000000000000000000000"
				+ " deadbeefdeadbeefdeadbeefdeadbeefdeadbeef"
				+ " refs/heads/master";
		ReceiveCommand cmd = BaseReceivePack.parseCommand(input);
		assertEquals(ObjectId.zeroId()
		assertEquals("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef"
				cmd.getNewId().name());
		assertEquals("refs/heads/master"
	}
}


package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class BaseReceivePackTest {
	private static final String CMD_STRING =
			"0000000000000000000000000000000000000000"
			+ " deadbeefdeadbeefdeadbeefdeadbeefdeadbeef"
			+ " refs/heads/master";
	@Test
	public void parseCommand() {
		String input = CMD_STRING;
		ReceiveCommand cmd = BaseReceivePack.parseCommand(input);
		assertEquals(ObjectId.zeroId()
		assertEquals("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef"
				cmd.getNewId().name());
		assertEquals("refs/heads/master"
	}

	@Test
	public void parseCommandWithTrailingNewline() {
		String input = CMD_STRING + '\n';
		ReceiveCommand cmd = BaseReceivePack.parseCommand(input);
		assertEquals(ObjectId.zeroId()
		assertEquals("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef"
				cmd.getNewId().name());
		assertEquals("refs/heads/master"
	}
}

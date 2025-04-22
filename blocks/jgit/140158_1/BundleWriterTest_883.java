
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class BaseReceivePackTest {
	@Test
	public void parseCommand() throws Exception {
		String o = "0000000000000000000000000000000000000000";
		String n = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
		String r = "refs/heads/master";
		ReceiveCommand cmd = BaseReceivePack.parseCommand(o + " " + n + " " + r);
		assertEquals(ObjectId.zeroId()
		assertEquals("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef"
				cmd.getNewId().name());
		assertEquals("refs/heads/master"

		assertParseCommandFails(null);
		assertParseCommandFails("");
		assertParseCommandFails(o.substring(35) + " " + n.substring(35)
				+ " " + r + "\n");
		assertParseCommandFails(o + " " + n + " " + r + "\n");
		assertParseCommandFails(o + " " + n + " " + "refs^foo");
		assertParseCommandFails(o + " " + n.substring(10) + " " + r);
		assertParseCommandFails(o.substring(10) + " " + n + " " + r);
		assertParseCommandFails("X" + o.substring(1) + " " + n + " " + r);
		assertParseCommandFails(o + " " + "X" + n.substring(1) + " " + r);
	}

	private void assertParseCommandFails(String input) {
		try {
			BaseReceivePack.parseCommand(input);
			fail();
		} catch (PackProtocolException e) {
		}
	}
}

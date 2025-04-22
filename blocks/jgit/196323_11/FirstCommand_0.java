
package org.eclipse.jgit.internal.transport.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class FirstCommandTest {
	@Test
	public void testClientSID() {
		String oldStr = "0000000000000000000000000000000000000000";
		String newStr = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
		String refName = "refs/heads/master";
		String command = oldStr + " " + newStr + " " + refName;
		String fl = command + "\0"
				+ "some capabilities session-id=the-clients-SID and more unknownCap=some-value";
		FirstCommand fc = FirstCommand.fromLine(fl);

		Map<String

		assertEquals("the-clients-SID"
		assertEquals(command
		assertTrue(options.containsKey("unknownCap"));
		assertEquals(6
	}
}

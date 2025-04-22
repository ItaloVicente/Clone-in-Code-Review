
package org.eclipse.jgit.internal.transport.parser;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class FirstCommandTest {
	@Test
	public void testClientSID() {
		String o = "0000000000000000000000000000000000000000";
		String n = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
		String r = "refs/heads/master";
		String command = o + " " + n + " " + r;
		String fl = command + "\0"
				+ "some capabilities session-id=the-clients-SID and more capabilities";
		FirstCommand fc = FirstCommand.fromLine(fl);

		Map<String

		assertEquals("the-clients-SID"
		assertEquals(command
	}
}

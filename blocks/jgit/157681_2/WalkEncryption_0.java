
package org.eclipse.jgit.util;

import static org.eclipse.jgit.util.Hex.decode;
import static org.eclipse.jgit.util.Hex.toHexString;
import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class HexTest {
	@Test
	public void testEncode() {
		assertEquals("68690a"
		assertEquals("0001020d0a0971"
	}

	@Test
	public void testDecode() {
		JGitTestUtil.assertEquals(b("hi\n")
		JGitTestUtil.assertEquals(b("\0\1\2\r\n\tq")
		JGitTestUtil.assertEquals(b("\u000EB")
	}

	@Test
	public void testEncodeMatchesDecode() {
		String[] testStrings = { ""
				"cow"
				"a"
				"a secret string"
		};
		for (String e : testStrings)
			JGitTestUtil.assertEquals(b(e)
	}

	private static byte[] b(String str) {
		return Constants.encode(str);
	}

}

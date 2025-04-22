
package org.eclipse.jgit.util;

import static org.eclipse.jgit.util.Base64.decode;
import static org.eclipse.jgit.util.Base64.encodeBytes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class Base64Test {
	@Test
	public void testEncode() {
		assertEquals("aGkK"
		assertEquals("AAECDQoJcQ=="
	}

	@Test
	public void testDecode() {
		JGitTestUtil.assertEquals(b("hi\n")
		JGitTestUtil.assertEquals(b("\0\1\2\r\n\tq")
		JGitTestUtil.assertEquals(b("\0\1\2\r\n\tq")
				decode("A A E\tC D\rQ o\nJ c Q=="));
		JGitTestUtil.assertEquals(b("\u000EB")
	}

	@Test
	public void testDecodeFail_NonBase64Character() {
		try {
			decode("! a bad base64 string !");
			fail("Accepted bad string in decode");
		} catch (IllegalArgumentException fail) {
		}
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

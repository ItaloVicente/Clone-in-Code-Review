
package org.eclipse.jgit.util;

import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class RawParseUtils_MatchTest {
	@Test
	public void testMatch_Equal() {
		final byte[] src = Constants.encodeASCII(" differ\n");
		final byte[] dst = Constants.encodeASCII("foo differ\n");
		assertTrue(RawParseUtils.match(dst
	}

	@Test
	public void testMatch_NotEqual() {
		final byte[] src = Constants.encodeASCII(" differ\n");
		final byte[] dst = Constants.encodeASCII("a differ\n");
		assertTrue(RawParseUtils.match(dst
	}

	@Test
	public void testMatch_Prefix() {
		final byte[] src = Constants.encodeASCII("author ");
		final byte[] dst = Constants.encodeASCII("author A. U. Thor");
		assertTrue(RawParseUtils.match(dst
		assertTrue(RawParseUtils.match(dst
	}

	@Test
	public void testMatch_TooSmall() {
		final byte[] src = Constants.encodeASCII("author ");
		final byte[] dst = Constants.encodeASCII("author autho");
		assertTrue(RawParseUtils.match(dst
	}
}

package org.eclipse.jgit.lib.internal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

public class BouncyCastleGpgKeyLocatorTest {

	private static final String USER_ID = "Heinrich Heine <heinrichh@uni-duesseldorf.de>";

	private static boolean match(String userId
		return BouncyCastleGpgKeyLocator.containsSigningKey(userId
	}

	@Test
	public void testFullMatch() throws Exception {
		assertTrue(match(USER_ID
				"=Heinrich Heine <heinrichh@uni-duesseldorf.de>"));
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
	}

	@Test
	public void testEmpty() throws Exception {
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(""
		assertFalse(match(null
		assertFalse(match(null
		assertFalse(match(""
		assertFalse(match(null
	}

	@Test
	public void testFullEmail() throws Exception {
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID + " "
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID.substring(0
				"<heinrichh@uni-duesseldorf.de>"));
	}

	@Test
	public void testPartialEmail() throws Exception {
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
	}

	private void substringTests(String prefix) throws Exception {
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertTrue(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
		assertFalse(match(USER_ID
	}

	@Test
	public void testSubstringPlain() throws Exception {
		substringTests("");
	}

	@Test
	public void testSubstringAsterisk() throws Exception {
		substringTests("*");
	}

	@Test
	public void testExplicitFingerprint() throws Exception {
		assertFalse(match("John Fade <j.fade@example.com>"
		assertFalse(match("John Fade <0xfade@example.com>"
	}

	@Test
	public void testImplicitFingerprint() throws Exception {
		assertTrue(match("John Fade <j.fade@example.com>"
		assertTrue(match("John Fade <0xfade@example.com>"
		assertTrue(match("John Fade <j.fade@example.com>"
		assertTrue(match("John Fade <0xfade@example.com>"
	}

	@Test
	public void testZeroX() throws Exception {
		assertTrue(match("John Fade <0xfade@example.com>"
		assertTrue(match("John Fade <0xfade@example.com>"
		assertTrue(match("John Fade <0xfade@example.com>"
		assertTrue(match("John Fade <0xfade@example.com>"
	}
}

package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Test;

public class RawSubStringPatternTest extends RepositoryTestCase {

	@Test
	public void testBoundary() {
		assertMatchResult("a"
		assertMatchResult("a"
		assertMatchResult("ab"
		assertMatchResult("abcd"
		assertMatchResult("bc"
		assertMatchResult("bcd"
		assertMatchResult("cd"
		assertMatchResult("d"
		assertMatchResult("abab"
	}

	@Test
	public void testNoMatches() {
		assertMatchResult("a"
		assertMatchResult("a"
		assertMatchResult("abab"
		assertMatchResult("ab"
	}

	@Test
	public void testCaseInsensitive() {
		assertMatchResult("a"
		assertMatchResult("A"
		assertMatchResult("Ab"
		assertMatchResult("aB"
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyPattern() {
		assertNotNull(new RawSubStringPattern(""));
	}

	private static void assertMatchResult(String pattern
		RawSubStringPattern p = new RawSubStringPattern(pattern);
		assertEquals("Expected match result " + position + " with input "
				+ input + " for pattern " + pattern
				position
	}

	private static RawCharSequence raw(String text) {
		try {
			byte[] bytes = text.getBytes("UTF-8");
			return new RawCharSequence(bytes
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}

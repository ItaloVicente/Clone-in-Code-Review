package org.eclipse.jgit.ignore;

import junit.framework.Assert;
import junit.framework.TestCase;


public class IgnoreMatcherTest  extends TestCase{

	public void testBasic() {
		String pattern = "/test.stp";
		assertMatched(pattern

		pattern = "#/test.stp";
		assertUnmatched(pattern
	}

	public void testFileNameWildcards() {
		String pattern = "*.st?";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern

		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern

		pattern = "*.sta[0-5]";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern

		pattern = "/[tv]est.sta[a-d]";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern

		pattern = "/src/ne?";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern

		pattern = "ne?";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern
	}

	public void testParentDirectoryGitIgnores() {

		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern

		pattern = "/src/new";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern

		pattern = "/src/new/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern

		pattern = "b1";
		assertMatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern
	}

	public void testTrailingSlash() {
		String pattern = "/src/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern
	}

	public void testNameOnlyMatches() {
		String pattern = "*.stp";
		assertMatched(pattern
		assertMatched(pattern
		assertUnmatched(pattern
		assertUnmatched(pattern

		pattern = "src";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern

		pattern = "?rc";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
	}

	public void testNegation() {
		String pattern = "!/test.stp";
		assertMatched(pattern
	}

	public void testGetters() {
		IgnoreRule r = new IgnoreRule("/pattern/");
		assertFalse(r.getNameOnly());
		assertTrue(r.getDirOnly());
		assertFalse(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("/patter?/");
		assertFalse(r.getNameOnly());
		assertTrue(r.getDirOnly());
		assertFalse(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("patt*");
		assertTrue(r.getNameOnly());
		assertFalse(r.getDirOnly());
		assertFalse(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("pattern");
		assertTrue(r.getNameOnly());
		assertFalse(r.getDirOnly());
		assertFalse(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("!pattern");
		assertTrue(r.getNameOnly());
		assertFalse(r.getDirOnly());
		assertTrue(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("!/pattern");
		assertFalse(r.getNameOnly());
		assertFalse(r.getDirOnly());
		assertTrue(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("!/patter?");
		assertFalse(r.getNameOnly());
		assertFalse(r.getDirOnly());
		assertTrue(r.getNegation());
		assertEquals(r.getPattern()
	}

	public void assertMatched(String pattern
		boolean value = match(pattern
		Assert.assertTrue("Expected a match for: " + pattern + " with: " + target
	}

	public void assertUnmatched(String pattern
		boolean value = match(pattern
		Assert.assertFalse("Expected no match for: " + pattern + " with: " + target
	}

	private boolean match(String pattern
		IgnoreRule r = new IgnoreRule(pattern);
		return r.isMatch(target
	}
}

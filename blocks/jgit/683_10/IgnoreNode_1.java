package org.eclipse.jgit.ignore;

import junit.framework.Assert;
import junit.framework.TestCase;


public class IgnoreMatcherTest  extends TestCase{

	public void testBasic() {
		String pattern = "/test.stp";
		assertMatched(pattern

		pattern = "#/test.stp";
		assertNotMatched(pattern
	}

	public void testFileNameWildcards() {
		String pattern = "*.st?";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern

		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern

		pattern = "*.sta[0-5]";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern

		pattern = "/[tv]est.sta[a-d]";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern

		pattern = "/src/ne?";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern

		pattern = "ne?";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
	}

	public void testParentDirectoryGitIgnores() {

		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern

		pattern = "/src/new";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern

		pattern = "/src/new/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern

		pattern = "b1";
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	public void testTrailingSlash() {
		String pattern = "/src/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	public void testNameOnlyMatches() {
		String pattern = "*.stp";
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern

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

		pattern = "?r[a-c]";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	public void testNegation() {
		String pattern = "!/test.stp";
		assertMatched(pattern
	}

	public void testGetters() {
		IgnoreRule r = new IgnoreRule("/pattern/");
		assertFalse(r.getNameOnly());
		assertTrue(r.dirOnly());
		assertFalse(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("/patter?/");
		assertFalse(r.getNameOnly());
		assertTrue(r.dirOnly());
		assertFalse(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("patt*");
		assertTrue(r.getNameOnly());
		assertFalse(r.dirOnly());
		assertFalse(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("pattern");
		assertTrue(r.getNameOnly());
		assertFalse(r.dirOnly());
		assertFalse(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("!pattern");
		assertTrue(r.getNameOnly());
		assertFalse(r.dirOnly());
		assertTrue(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("!/pattern");
		assertFalse(r.getNameOnly());
		assertFalse(r.dirOnly());
		assertTrue(r.getNegation());
		assertEquals(r.getPattern()

		r = new IgnoreRule("!/patter?");
		assertFalse(r.getNameOnly());
		assertFalse(r.dirOnly());
		assertTrue(r.getNegation());
		assertEquals(r.getPattern()
	}

	public void assertMatched(String pattern
		boolean value = match(pattern
		Assert.assertTrue("Expected a match for: " + pattern + " with: " + target
	}

	public void assertNotMatched(String pattern
		boolean value = match(pattern
		Assert.assertFalse("Expected no match for: " + pattern + " with: " + target
	}

	private boolean match(String pattern
		IgnoreRule r = new IgnoreRule(pattern);
		return r.isMatch(target
	}
}

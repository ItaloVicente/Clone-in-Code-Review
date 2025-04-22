package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AttributesMatcherTest {

	@Test
	public void testBasic() {
		String pattern = "/test.stp";
		assertMatched(pattern

		pattern = "#/test.stp";
		assertNotMatched(pattern
	}

	@Test
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

	@Test
	public void testTargetWithoutLeadingSlash() {
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

	@Test
	public void testParentDirectoryGitAttributes() {

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

	@Test
	public void testTrailingSlash() {
		String pattern = "/src/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	@Test
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
		assertMatched(pattern

		pattern = "src/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern

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

	@Test
	public void testGetters() {
		AttributesRule r = new AttributesRule("/pattern/"
		assertFalse(r.isNameOnly());
		assertTrue(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertTrue(r.getAttributes().isEmpty());
		assertEquals(r.getPattern()

		r = new AttributesRule("/patter?/"
		assertFalse(r.isNameOnly());
		assertTrue(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertTrue(r.getAttributes().isEmpty());
		assertEquals(r.getPattern()

		r = new AttributesRule("patt*"
		assertTrue(r.isNameOnly());
		assertFalse(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertTrue(r.getAttributes().isEmpty());
		assertEquals(r.getPattern()

		r = new AttributesRule("pattern"
		assertTrue(r.isNameOnly());
		assertFalse(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertFalse(r.getAttributes().isEmpty());
		assertEquals(r.getAttributes().size()
		assertEquals(r.getPattern()

		r = new AttributesRule("pattern"
		assertTrue(r.isNameOnly());
		assertFalse(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertEquals(r.getAttributes().size()
		assertEquals(r.getPattern()

		r = new AttributesRule("pattern"
		assertTrue(r.isNameOnly());
		assertFalse(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertEquals(r.getAttributes().size()
		assertEquals(r.getPattern()

		r = new AttributesRule("pattern"
		assertTrue(r.isNameOnly());
		assertFalse(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertEquals(r.getAttributes().size()
		assertEquals(r.getPattern()

		r = new AttributesRule("pattern"
		assertTrue(r.isNameOnly());
		assertFalse(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertEquals(r.getAttributes().size()
		assertEquals(r.getPattern()

		r = new AttributesRule("pattern"
				"attribute1 -attribute2  attribute3=value ");
		assertTrue(r.isNameOnly());
		assertFalse(r.dirOnly());
		assertNotNull(r.getAttributes());
		assertEquals(r.getAttributes().size()
		assertEquals(r.getPattern()
		assertEquals(r.getAttributes().get(0).toString()
		assertEquals(r.getAttributes().get(1).toString()
		assertEquals(r.getAttributes().get(2).toString()
	}

	public void assertMatched(String pattern
		boolean value = match(pattern
		assertTrue("Expected a match for: " + pattern + " with: " + target
				value);
	}

	public void assertNotMatched(String pattern
		boolean value = match(pattern
		assertFalse("Expected no match for: " + pattern + " with: " + target
				value);
	}

	private static boolean match(String pattern
		AttributesRule r = new AttributesRule(pattern
		return r.isMatch(target
	}
}

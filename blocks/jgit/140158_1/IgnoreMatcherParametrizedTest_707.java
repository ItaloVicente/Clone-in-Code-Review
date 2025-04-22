package org.eclipse.jgit.ignore;

import static org.eclipse.jgit.ignore.internal.Strings.split;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class FastIgnoreRuleTest {

	private boolean pathMatch;

	@Before
	public void setup() {
		pathMatch = false;
	}

	@Test
	public void testSimpleCharClass() {
		assertMatched("][a]"
		assertMatched("[a]"
		assertMatched("][a]"
		assertMatched("[a]"
		assertMatched("[a]"

		assertMatched("[a]"
		assertMatched("[a]"
		assertMatched("[a]"

		assertMatched("[a]"
		assertMatched("[a]"

		assertMatched("[a]"
		assertMatched("[a]"

		assertMatched("/[a]"
		assertMatched("/[a]"
		assertMatched("/[a]"
		assertMatched("/[a]"
		assertMatched("/[a]"
		assertMatched("/[a]"

		assertMatched("[a]/"
		assertMatched("[a]/"
		assertMatched("[a]/"
		assertMatched("[a]/"

		assertMatched("/[a]/"
		assertMatched("/[a]/"
		assertMatched("/[a]/"
		assertMatched("/[a]/"
	}

	@Test
	public void testCharClass() {
		assertMatched("[v-z]"
		assertMatched("[v-z]"
		assertMatched("[v-z]"

		assertMatched("[v-z]"
		assertMatched("[v-z]"
		assertMatched("[v-z]"

		assertMatched("[v-z]"
		assertMatched("[v-z]"

		assertMatched("[v-z]"
		assertMatched("[v-z]"

		assertMatched("/[v-z]"
		assertMatched("/[v-z]"
		assertMatched("/[v-z]"
		assertMatched("/[v-z]"
		assertMatched("/[v-z]"
		assertMatched("/[v-z]"

		assertMatched("[v-z]/"
		assertMatched("[v-z]/"
		assertMatched("[v-z]/"
		assertMatched("[v-z]/"

		assertMatched("/[v-z]/"
		assertMatched("/[v-z]/"
		assertMatched("/[v-z]/"
		assertMatched("/[v-z]/"
	}

	@Test
	public void testTrailingSpaces() {
		assertMatched("a "
		assertMatched("a/ "
		assertMatched("a/ "
		assertMatched("a/\\ "
		assertNotMatched("a/\\ "
		assertNotMatched("a/\\ "
		assertNotMatched("/ "
	}

	@Test
	public void testAsteriskDot() {
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
	}

	@Test
	public void testAsteriskDotDoNotMatch() {
		assertNotMatched("*.a"
		assertNotMatched("*.a"
		assertNotMatched("*.a"
		assertNotMatched("*.a"
		assertNotMatched("*.a"
		assertNotMatched("*.a"
		assertNotMatched("*.a"
		assertNotMatched("*.a"
	}

	@Test
	public void testDotAsteriskMatch() {
		assertMatched("a.*"
		assertMatched("a.*"
		assertMatched("a.*"

		assertMatched("a.*"
		assertMatched("a.*"
		assertMatched("a.*"

		assertMatched("a.*"
		assertMatched("a.*"

		assertMatched("a.*"
		assertMatched("a.*"

		assertMatched("/a.*"
		assertMatched("/a.*"
		assertMatched("/a.*"
		assertMatched("/a.*"
		assertMatched("/a.*"
		assertMatched("/a.*"

	}

	@Test
	public void testAsterisk() {
		assertMatched("a*"
		assertMatched("a*"
		assertMatched("a*"

		assertMatched("a*"
		assertMatched("a*"
		assertMatched("a*"

		assertMatched("a*"
		assertMatched("a*"
		assertMatched("a*"

		assertMatched("a*"
		assertMatched("a*"

		assertMatched("a*"
		assertMatched("a*"

		assertMatched("/a*"
		assertMatched("/a*"
		assertMatched("/a*"
		assertMatched("/a*"
		assertMatched("/a*"
		assertMatched("/a*"

	}

	@Test
	public void testQuestionmark() {
		assertMatched("a?"
		assertMatched("a?"

		assertMatched("a?"
		assertMatched("a?"
		assertMatched("a?"

		assertMatched("a?"
		assertMatched("a?"

		assertMatched("a?"
		assertMatched("a?"

		assertMatched("/a?"
		assertMatched("/a?"
		assertMatched("/a?"
		assertMatched("/a?"
		assertMatched("/a?"
		assertMatched("/a?"

		assertMatched("/a?/b"
		assertMatched("/a?/b"
	}

	@Test
	public void testQuestionmarkDoNotMatch() {
		assertNotMatched("a?"
		assertNotMatched("a?"
		assertNotMatched("a?"

		assertNotMatched("a?"
		assertNotMatched("a?"

		assertNotMatched("a?"
		assertNotMatched("a?"

		assertNotMatched("a?"
		assertNotMatched("a?"

		assertNotMatched("/a?"
		assertNotMatched("/a?"
		assertNotMatched("/a?"
		assertNotMatched("/a?"
		assertNotMatched("/a?"
		assertNotMatched("/a?"

		assertNotMatched("/a?/b"
		assertNotMatched("/a?/b"
		assertNotMatched("/a?/b"
	}

	@Test
	public void testSimplePatterns() {
		assertMatched("a"
		assertMatched("a"
		assertMatched("a"

		assertMatched("a"
		assertMatched("a"
		assertMatched("a"

		assertMatched("a"
		assertMatched("a"

		assertMatched("a"
		assertMatched("a"

		assertMatched("/a"
		assertMatched("/a"
		assertMatched("/a"
		assertMatched("/a"
		assertMatched("/a"
		assertMatched("/a"

		assertMatched("a/"
		assertMatched("a/"
		assertMatched("a/"
		assertMatched("a/"

		assertMatched("/a/"
		assertMatched("/a/"
		assertMatched("/a/"
		assertMatched("/a/"

	}

	@Test
	public void testSimplePatternsDoNotMatch() {
		assertNotMatched("ab"
		assertNotMatched("abc"
		assertNotMatched("abc"

		assertNotMatched("a"
		assertNotMatched("a"
		assertNotMatched("a"

		assertNotMatched("a"
		assertNotMatched("a"

		assertNotMatched("a"
		assertNotMatched("a"

		assertNotMatched("a"
		assertNotMatched("a"

		assertNotMatched("a"
		assertNotMatched("a"
		assertNotMatched("a"

		assertNotMatched("/a"
		assertNotMatched("/a"

		assertNotMatched("a/"
		assertNotMatched("a/"

		assertNotMatched("/a/"
		assertNotMatched("/a/"
		assertNotMatched("/a/"
	}

	@Test
	public void testSegments() {
		assertMatched("/a/b"
		assertMatched("/a/b"
		assertMatched("/a/b"
		assertMatched("/a/b"

		assertMatched("a/b"
		assertMatched("a/b"
		assertMatched("a/b"
		assertMatched("a/b"

		assertMatched("a/b/"
		assertMatched("a/b/"
		assertMatched("a/b/"
	}

	@Test
	public void testSegmentsDoNotMatch() {
		assertNotMatched("a/b"
		assertNotMatched("a/b"
		assertNotMatched("a/b"
		assertNotMatched("a/b"
		assertNotMatched("a/b"
		assertNotMatched("a/b"
		assertNotMatched("a/b/"
		assertNotMatched("/a/b/"
		assertNotMatched("/a/b"
		assertNotMatched("/a/b/"
		assertNotMatched("/a/b/"

		assertNotMatched("a/b"
		assertNotMatched("a/b"
		assertNotMatched("a/b"
		assertNotMatched("a/b/"
		assertNotMatched("a/b/"
	}

	@Test
	public void testWildmatch() {







	}

	@Test
	public void testWildmatchDoNotMatch() {

	}

	@SuppressWarnings("unused")
	@Test
	public void testSimpleRules() {
		try {
			new FastIgnoreRule(null);
			fail("Illegal input allowed!");
		} catch (IllegalArgumentException e) {
		}
		assertFalse(new FastIgnoreRule("/").isMatch("/"
		assertFalse(new FastIgnoreRule("#").isMatch("#"
		assertFalse(new FastIgnoreRule("").isMatch(""
		assertFalse(new FastIgnoreRule(" ").isMatch(" "
	}

	@Test
	public void testSplit() {
		try {
			split("/"
			fail("should not allow single slash");
		} catch (IllegalStateException e) {
		}

		assertArrayEquals(new String[] { "a"
				.toArray());
		assertArrayEquals(new String[] { "a"
				.toArray());
		assertArrayEquals(new String[] { "/a"
				.toArray());
		assertArrayEquals(new String[] { "/a"
				.toArray());
		assertArrayEquals(new String[] { "/a"
				.toArray());
		assertArrayEquals(new String[] { "/a"
				split("/a/b/c/"
	}

	@Test
	public void testPathMatch() {
		pathMatch = true;
		assertMatched("a"
		assertMatched("a/"
		assertNotMatched("a/"

		assertMatched("**"
		assertMatched("**"
		assertMatched("**"


	}

	@Test
	public void testFileNameWithLineTerminator() {
		assertMatched("a?"
		assertMatched("a?"
		assertMatched("a?"
		assertMatched("*a"
	}

	private void assertMatched(String pattern
		boolean match = match(pattern
		String result = path + " is " + (match ? "ignored" : "not ignored")
				+ " via '" + pattern + "' rule";
		if (!match) {
			System.err.println(result);
		}
		assertTrue("Expected a match for: " + pattern + " with: " + path
					match);

		if (pattern.startsWith("!")) {
			pattern = pattern.substring(1);
		} else {
			pattern = "!" + pattern;
		}
		match = match(pattern
		assertFalse("Expected no match for: " + pattern + " with: " + path
				match);
	}

	private void assertNotMatched(String pattern
		boolean match = match(pattern
		String result = path + " is " + (match ? "ignored" : "not ignored")
				+ " via '" + pattern + "' rule";
		if (match) {
			System.err.println(result);
		}
		assertFalse("Expected no match for: " + pattern + " with: " + path
					match);

		if (pattern.startsWith("!")) {
			pattern = pattern.substring(1);
		} else {
			pattern = "!" + pattern;
		}
		match = match(pattern
		assertTrue("Expected a match for: " + pattern + " with: " + path
					match);
	}

	private boolean match(String pattern
		boolean isDirectory = target.endsWith("/");
		FastIgnoreRule r = new FastIgnoreRule(pattern);
		boolean match = r.isMatch(target
		if (r.getNegation())
			match = !match;
		return match;
	}
}

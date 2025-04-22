package org.eclipse.jgit.ignore;

import static org.eclipse.jgit.ignore.internal.Strings.split;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.util.Arrays;

import org.eclipse.jgit.ignore.FastIgnoreRule;
import org.eclipse.jgit.ignore.IgnoreRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("deprecation")
@RunWith(Parameterized.class)
public class FastIgnoreRuleTest {

	@Parameters(name = "JGit? {0}")
	public static Iterable<Boolean[]> data() {
		return Arrays.asList(new Boolean[][] { { Boolean.FALSE }
				{ Boolean.TRUE } });
	}

	@Parameter
	public Boolean useJGitRule;

	@Test
	public void testSimpleCharClass() {
		assertMatched("[a]"
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
	public void testDotAsterisk() {
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
		assertMatched("*.a"
	}

	@Test
	public void testDotAsteriskDoNotMatch() {
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
	public void testAsteriskDotMatch() {
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

	@SuppressWarnings("boxing")
	@Test
	public void testWildmatch() {
		if (useJGitRule)
			System.err
					.println("IgnoreRule can't understand wildmatch rules

		Boolean assume = useJGitRule;




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

	public void assertMatched(String pattern
		boolean match = match(pattern
		String result = path + " is " + (match ? "ignored" : "not ignored")
				+ " via '" + pattern + "' rule";
		if (!match)
			System.err.println(result);
		if (assume.length == 0 || !assume[0].booleanValue())
			assertTrue("Expected a match for: " + pattern + " with: " + path
					match);
		else
			assumeTrue("Expected a match for: " + pattern + " with: " + path
					match);

		if (pattern.startsWith("!"))
			pattern = pattern.substring(1);
		else
			pattern = "!" + pattern;
		match = match(pattern
		if (assume.length == 0 || !assume[0].booleanValue())
			assertFalse("Expected no match for: " + pattern + " with: " + path
					match);
		else
			assumeFalse("Expected no match for: " + pattern + " with: " + path
					match);
	}

	public void assertNotMatched(String pattern
		boolean match = match(pattern
		String result = path + " is " + (match ? "ignored" : "not ignored")
				+ " via '" + pattern + "' rule";
		if (match)
			System.err.println(result);
		if (assume.length == 0 || !assume[0].booleanValue())
			assertFalse("Expected no match for: " + pattern + " with: " + path
					match);
		else
			assumeFalse("Expected no match for: " + pattern + " with: " + path
					match);

		if (pattern.startsWith("!"))
			pattern = pattern.substring(1);
		else
			pattern = "!" + pattern;
		match = match(pattern
		if (assume.length == 0 || !assume[0].booleanValue())
			assertTrue("Expected a match for: " + pattern + " with: " + path
					match);
		else
			assumeTrue("Expected a match for: " + pattern + " with: " + path
					match);
	}

	private boolean match(String pattern
		boolean isDirectory = target.endsWith("/");
		if (useJGitRule.booleanValue()) {
			IgnoreRule r = new IgnoreRule(pattern);
			boolean match = r.isMatch(target
			if (r.getNegation())
				match = !match;
			return match;
		}
		FastIgnoreRule r = new FastIgnoreRule(pattern);
		boolean match = r.isMatch(target
		if (r.getNegation())
			match = !match;
		return match;
	}
}

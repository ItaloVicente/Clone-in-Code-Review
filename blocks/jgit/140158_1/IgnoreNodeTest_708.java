package org.eclipse.jgit.ignore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import org.eclipse.jgit.junit.Assert;
import org.junit.Test;

public class IgnoreMatcherParametrizedTest {

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

	@Test
	public void testDirModeAndNoRegex() {
		String pattern = "/src/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	@Test
	public void testDirModeAndRegex1() {
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	@Test
	public void testDirModeAndRegex2() {
		String pattern = "a/[a-b]/src/";
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertMatched(pattern
		assertNotMatched(pattern
		assertNotMatched(pattern
	}

	@Test
	public void testDirModeAndRegex3() {
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
	public void testNegation() {
		String pattern = "!/test.stp";
		assertMatched(pattern
	}

	private void assertMatched(String pattern
		boolean value = match(pattern
		if (assume.length == 0 || !assume[0].booleanValue())
			assertTrue("Expected a match for: " + pattern + " with: " + target
					value);
		else
			assumeTrue("Expected a match for: " + pattern + " with: " + target
					value);
	}

	private void assertNotMatched(String pattern
			Boolean... assume) {
		boolean value = match(pattern
		if (assume.length == 0 || !assume[0].booleanValue())
			assertFalse("Expected no match for: " + pattern + " with: "
					+ target
		else
			assumeFalse("Expected no match for: " + pattern + " with: "
					+ target
	}

	private boolean match(String pattern
		boolean isDirectory = target.endsWith("/");
		boolean match;
		FastIgnoreRule r = new FastIgnoreRule(pattern);
		match = r.isMatch(target

		if (isDirectory) {
			boolean noTrailingSlash = matchAsDir(pattern
					target.substring(0
			if (match != noTrailingSlash) {
				String message = "Difference in result for directory pattern: "
						+ pattern + " with: " + target
						+ " if target is given without trailing slash";
				Assert.assertEquals(message
			}
		}
		return match;
	}

	private boolean matchAsDir(String pattern
		assertFalse(target.endsWith("/"));
		FastIgnoreRule r = new FastIgnoreRule(pattern);
		return r.isMatch(target
	}
}

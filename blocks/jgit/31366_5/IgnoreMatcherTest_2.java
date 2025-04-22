package org.eclipse.jgit.ignore;

import static org.junit.Assert.*;

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
public class IgnoreMatcherParametrizedTest {

	@Parameters(name = "JGit? {0}")
	public static Iterable<Boolean[]> data() {
		return Arrays.asList(new Boolean[][] { { Boolean.FALSE }
				{ Boolean.TRUE } });
	}

	@Parameter
	public Boolean useJGitRule;

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
	public void testNegation() {
		String pattern = "!/test.stp";
		assertMatched(pattern
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

	private boolean match(String pattern
		boolean isDirectory = target.endsWith("/");
		if (useJGitRule.booleanValue()) {
			IgnoreRule r = new IgnoreRule(pattern);
			return r.isMatch(target
		}
		FastIgnoreRule r = new FastIgnoreRule(pattern);
		return r.isMatch(target
	}
}

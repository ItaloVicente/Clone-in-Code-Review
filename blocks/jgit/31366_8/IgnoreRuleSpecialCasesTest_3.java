package org.eclipse.jgit.ignore;

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

@RunWith(Parameterized.class)
@SuppressWarnings({ "deprecation"
public class IgnoreRuleSpecialCasesTest {

	@Parameters(name = "JGit? {0}")
	public static Iterable<Boolean[]> data() {
		return Arrays.asList(new Boolean[][] { { Boolean.FALSE }
				{ Boolean.TRUE } });
	}

	@Parameter
	public Boolean useJGitRule;

	private void assertMatch(final String pattern
			final boolean matchExpected
		boolean assumeDir = input.endsWith("/");
		if (useJGitRule.booleanValue()) {
			final IgnoreRule matcher = new IgnoreRule(pattern);
			if (assume.length == 0 || !assume[0].booleanValue())
				assertEquals(matchExpected
			else
				assumeTrue(matchExpected == matcher.isMatch(input
		} else {
			FastIgnoreRule matcher = new FastIgnoreRule(pattern);
			if (assume.length == 0 || !assume[0].booleanValue())
				assertEquals(matchExpected
			else
				assumeTrue(matchExpected == matcher.isMatch(input
		}
	}

	private void assertFileNameMatch(final String pattern
			final boolean matchExpected) {
		boolean assumeDir = input.endsWith("/");
		if (useJGitRule.booleanValue()) {
			final IgnoreRule matcher = new IgnoreRule(pattern);
			assertEquals(matchExpected
		} else {
			FastIgnoreRule matcher = new FastIgnoreRule(pattern);
			assertEquals(matchExpected
		}
	}

	@Test
	public void testVerySimplePatternCase0() throws Exception {
		if (useJGitRule)
			System.err
					.println("IgnoreRule can't understand blank lines
		Boolean assume = useJGitRule;
		assertMatch(""
	}

	@Test
	public void testVerySimplePatternCase1() throws Exception {
		assertMatch("ab"
	}

	@Test
	public void testVerySimplePatternCase2() throws Exception {
		assertMatch("ab"
	}

	@Test
	public void testVerySimplePatternCase3() throws Exception {
		assertMatch("ab"
	}

	@Test
	public void testVerySimplePatternCase4() throws Exception {
		assertMatch("ab"
	}

	@Test
	public void testVerySimpleWildcardCase0() throws Exception {
		assertMatch("?"
	}

	@Test
	public void testVerySimpleWildCardCase1() throws Exception {
		assertMatch("??"
	}

	@Test
	public void testVerySimpleWildCardCase2() throws Exception {
		assertMatch("??"
	}

	@Test
	public void testVerySimpleWildCardCase3() throws Exception {
		assertMatch("??"
	}

	@Test
	public void testVerySimpleStarCase0() throws Exception {
		assertMatch("*"
	}

	@Test
	public void testVerySimpleStarCase1() throws Exception {
		assertMatch("*"
	}

	@Test
	public void testVerySimpleStarCase2() throws Exception {
		assertMatch("*"
	}

	@Test
	public void testSimpleStarCase0() throws Exception {
		assertMatch("a*b"
	}

	@Test
	public void testSimpleStarCase1() throws Exception {
		assertMatch("a*c"
	}

	@Test
	public void testSimpleStarCase2() throws Exception {
		assertMatch("a*c"
	}

	@Test
	public void testSimpleStarCase3() throws Exception {
		assertMatch("a*c"
	}

	@Test
	public void testManySolutionsCase0() throws Exception {
		assertMatch("a*a*a"
	}

	@Test
	public void testManySolutionsCase1() throws Exception {
		assertMatch("a*a*a"
	}

	@Test
	public void testManySolutionsCase2() throws Exception {
		assertMatch("a*a*a"
	}

	@Test
	public void testManySolutionsCase3() throws Exception {
		assertMatch("a*a*a"
	}

	@Test
	public void testManySolutionsCase4() throws Exception {
		assertMatch("a*a*a"
	}

	@Test
	public void testVerySimpleGroupCase0() throws Exception {
		assertMatch("[ab]"
	}

	@Test
	public void testVerySimpleGroupCase1() throws Exception {
		assertMatch("[ab]"
	}

	@Test
	public void testVerySimpleGroupCase2() throws Exception {
		assertMatch("[ab]"
	}

	@Test
	public void testVerySimpleGroupRangeCase0() throws Exception {
		assertMatch("[b-d]"
	}

	@Test
	public void testVerySimpleGroupRangeCase1() throws Exception {
		assertMatch("[b-d]"
	}

	@Test
	public void testVerySimpleGroupRangeCase2() throws Exception {
		assertMatch("[b-d]"
	}

	@Test
	public void testVerySimpleGroupRangeCase3() throws Exception {
		assertMatch("[b-d]"
	}

	@Test
	public void testVerySimpleGroupRangeCase4() throws Exception {
		assertMatch("[b-d]"
	}

	@Test
	public void testVerySimpleGroupRangeCase5() throws Exception {
		assertMatch("[b-d]"
	}

	@Test
	public void testTwoGroupsCase0() throws Exception {
		assertMatch("[b-d][ab]"
	}

	@Test
	public void testTwoGroupsCase1() throws Exception {
		assertMatch("[b-d][ab]"
	}

	@Test
	public void testTwoGroupsCase2() throws Exception {
		assertMatch("[b-d][ab]"
	}

	@Test
	public void testTwoGroupsCase3() throws Exception {
		assertMatch("[b-d][ab]"
	}

	@Test
	public void testTwoRangesInOneGroupCase0() throws Exception {
		assertMatch("[b-ce-e]"
	}

	@Test
	public void testTwoRangesInOneGroupCase1() throws Exception {
		assertMatch("[b-ce-e]"
	}

	@Test
	public void testTwoRangesInOneGroupCase2() throws Exception {
		assertMatch("[b-ce-e]"
	}

	@Test
	public void testTwoRangesInOneGroupCase3() throws Exception {
		assertMatch("[b-ce-e]"
	}

	@Test
	public void testTwoRangesInOneGroupCase4() throws Exception {
		assertMatch("[b-ce-e]"
	}

	@Test
	public void testTwoRangesInOneGroupCase5() throws Exception {
		assertMatch("[b-ce-e]"
	}

	@Test
	public void testIncompleteRangesInOneGroupCase0() throws Exception {
		assertMatch("a[b-]"
	}

	@Test
	public void testIncompleteRangesInOneGroupCase1() throws Exception {
		assertMatch("a[b-]"
	}

	@Test
	public void testIncompleteRangesInOneGroupCase2() throws Exception {
		assertMatch("a[b-]"
	}

	@Test
	public void testCombinedRangesInOneGroupCase0() throws Exception {
		assertMatch("[a-c-e]"
	}

	@Test
	public void testCombinedRangesInOneGroupCase1() throws Exception {
		assertMatch("[a-c-e]"
	}

	@Test
	public void testCombinedRangesInOneGroupCase2() throws Exception {
		assertMatch("[a-c-e]"
	}

	@Test
	public void testInversedGroupCase0() throws Exception {
		assertMatch("[!b-c]"
	}

	@Test
	public void testInversedGroupCase1() throws Exception {
		assertMatch("[!b-c]"
	}

	@Test
	public void testInversedGroupCase2() throws Exception {
		assertMatch("[!b-c]"
	}

	@Test
	public void testInversedGroupCase3() throws Exception {
		assertMatch("[!b-c]"
	}

	@Test
	public void testAlphaGroupCase0() throws Exception {
		assertMatch("[[:alpha:]]"
	}

	@Test
	public void testAlphaGroupCase1() throws Exception {
		assertMatch("[[:alpha:]]"
	}

	@Test
	public void testAlphaGroupCase2() throws Exception {
		assertMatch("[[:alpha:]]"
	}

	@Test
	public void test2AlphaGroupsCase0() throws Exception {
		assertMatch("[[:alpha:]][[:alpha:]]"
		assertMatch("[[:alpha:]][[:alpha:]]"
	}

	@Test
	public void testAlnumGroupCase0() throws Exception {
		assertMatch("[[:alnum:]]"
	}

	@Test
	public void testAlnumGroupCase1() throws Exception {
		assertMatch("[[:alnum:]]"
	}

	@Test
	public void testAlnumGroupCase2() throws Exception {
		assertMatch("[[:alnum:]]"
	}

	@Test
	public void testBlankGroupCase0() throws Exception {
		assertMatch("[[:blank:]]"
	}

	@Test
	public void testBlankGroupCase1() throws Exception {
		assertMatch("[[:blank:]]"
	}

	@Test
	public void testBlankGroupCase2() throws Exception {
		assertMatch("[[:blank:]]"
	}

	@Test
	public void testBlankGroupCase3() throws Exception {
		assertMatch("[[:blank:]]"
	}

	@Test
	public void testBlankGroupCase4() throws Exception {
		assertMatch("[[:blank:]]"
	}

	@Test
	public void testCntrlGroupCase0() throws Exception {
		assertMatch("[[:cntrl:]]"
	}

	@Test
	public void testCntrlGroupCase1() throws Exception {
		assertMatch("[[:cntrl:]]"
	}

	@Test
	public void testDigitGroupCase0() throws Exception {
		assertMatch("[[:digit:]]"
	}

	@Test
	public void testDigitGroupCase1() throws Exception {
		assertMatch("[[:digit:]]"
	}

	@Test
	public void testDigitGroupCase2() throws Exception {
		assertMatch("[[:digit:]]"
	}

	@Test
	public void testDigitGroupCase3() throws Exception {
		assertMatch("[[:digit:]]"
	}

	@Test
	public void testDigitGroupCase4() throws Exception {
		assertMatch("[[:digit:]]"
	}

	@Test
	public void testDigitGroupCase5() throws Exception {
		assertMatch("[[:digit:]]"
	}

	@Test
	public void testGraphGroupCase0() throws Exception {
		assertMatch("[[:graph:]]"
	}

	@Test
	public void testGraphGroupCase1() throws Exception {
		assertMatch("[[:graph:]]"
	}

	@Test
	public void testGraphGroupCase2() throws Exception {
		assertMatch("[[:graph:]]"
	}

	@Test
	public void testGraphGroupCase3() throws Exception {
		assertMatch("[[:graph:]]"
	}

	@Test
	public void testGraphGroupCase4() throws Exception {
		assertMatch("[[:graph:]]"
	}

	@Test
	public void testGraphGroupCase5() throws Exception {
		assertMatch("[[:graph:]]"
	}

	@Test
	public void testLowerGroupCase0() throws Exception {
		assertMatch("[[:lower:]]"
	}

	@Test
	public void testLowerGroupCase1() throws Exception {
		assertMatch("[[:lower:]]"
	}

	@Test
	public void testLowerGroupCase2() throws Exception {
		assertMatch("[[:lower:]]"
	}

	@Test
	public void testLowerGroupCase3() throws Exception {
		assertMatch("[[:lower:]]"
	}

	@Test
	public void testLowerGroupCase4() throws Exception {
		assertMatch("[[:lower:]]"
	}

	@Test
	public void testLowerGroupCase5() throws Exception {
		assertMatch("[[:lower:]]"
	}

	@Test
	public void testPrintGroupCase0() throws Exception {
		assertMatch("[[:print:]]"
	}

	@Test
	public void testPrintGroupCase1() throws Exception {
		assertMatch("[[:print:]]"
	}

	@Test
	public void testPrintGroupCase2() throws Exception {
		assertMatch("[[:print:]]"
	}

	@Test
	public void testPrintGroupCase3() throws Exception {
		assertMatch("[[:print:]]"
	}

	@Test
	public void testPrintGroupCase4() throws Exception {
		assertMatch("[[:print:]]"
	}

	@Test
	public void testPrintGroupCase5() throws Exception {
		assertMatch("[[:print:]]"
	}

	@Test
	public void testPunctGroupCase0() throws Exception {
		assertMatch("[[:punct:]]"
	}

	@Test
	public void testPunctGroupCase1() throws Exception {
		assertMatch("[[:punct:]]"
	}

	@Test
	public void testPunctGroupCase2() throws Exception {
		assertMatch("[[:punct:]]"
	}

	@Test
	public void testPunctGroupCase3() throws Exception {
		assertMatch("[[:punct:]]"
	}

	@Test
	public void testSpaceGroupCase0() throws Exception {
		assertMatch("[[:space:]]"
	}

	@Test
	public void testSpaceGroupCase1() throws Exception {
		assertMatch("[[:space:]]"
	}

	@Test
	public void testSpaceGroupCase2() throws Exception {
		assertMatch("[[:space:]]"
	}

	@Test
	public void testSpaceGroupCase3() throws Exception {
		assertMatch("[[:space:]]"
	}

	@Test
	public void testSpaceGroupCase4() throws Exception {
		assertMatch("[[:space:]]"
	}

	@Test
	public void testUpperGroupCase0() throws Exception {
		assertMatch("[[:upper:]]"
	}

	@Test
	public void testUpperGroupCase1() throws Exception {
		assertMatch("[[:upper:]]"
	}

	@Test
	public void testUpperGroupCase2() throws Exception {
		assertMatch("[[:upper:]]"
	}

	@Test
	public void testUpperGroupCase3() throws Exception {
		assertMatch("[[:upper:]]"
	}

	@Test
	public void testUpperGroupCase4() throws Exception {
		assertMatch("[[:upper:]]"
	}

	@Test
	public void testUpperGroupCase5() throws Exception {
		assertMatch("[[:upper:]]"
	}

	@Test
	public void testXDigitGroupCase0() throws Exception {
		assertMatch("[[:xdigit:]]"
	}

	@Test
	public void testXDigitGroupCase1() throws Exception {
		assertMatch("[[:xdigit:]]"
	}

	@Test
	public void testXDigitGroupCase2() throws Exception {
		assertMatch("[[:xdigit:]]"
	}

	@Test
	public void testXDigitGroupCase3() throws Exception {
		assertMatch("[[:xdigit:]]"
	}

	@Test
	public void testXDigitGroupCase4() throws Exception {
		assertMatch("[[:xdigit:]]"
	}

	@Test
	public void testXDigitGroupCase5() throws Exception {
		assertMatch("[[:xdigit:]]"
	}

	@Test
	public void testXDigitGroupCase6() throws Exception {
		assertMatch("[[:xdigit:]]"
	}

	@Test
	public void testXDigitGroupCase7() throws Exception {
		assertMatch("[[:xdigit:]]"
	}

	@Test
	public void testWordGroupCase0() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordGroupCase1() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordGroupCase2() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordGroupCase3() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordGroupCase4() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordGroupCase5() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testMixedGroupCase0() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase1() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase2() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase3() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase4() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase5() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase6() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase7() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase8() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testMixedGroupCase9() throws Exception {
		assertMatch("[A[:lower:]C3-5]"
	}

	@Test
	public void testSpecialGroupCase0() throws Exception {
		assertMatch("[[]"
	}

	@Test
	public void testSpecialGroupCase1() throws Exception {
		assertMatch("[]]"
	}

	@Test
	public void testSpecialGroupCase2() throws Exception {
		assertMatch("[]a]"
	}

	@Test
	public void testSpecialGroupCase3() throws Exception {
		assertMatch("[a[]"
	}

	@Test
	public void testSpecialGroupCase4() throws Exception {
		assertMatch("[a[]"
	}

	@Test
	public void testSpecialGroupCase5() throws Exception {
		assertMatch("[!]]"
	}

	@Test
	public void testSpecialGroupCase6() throws Exception {
		assertMatch("[!]]"
	}

	@Test
	public void testSpecialGroupCase7() throws Exception {
		assertMatch("[:]]"
	}

	@Test
	public void testSpecialGroupCase8() throws Exception {
		assertMatch("[:]]"
	}

	@Test
	public void testSpecialGroupCase9() throws Exception {
		if (useJGitRule)
			System.err.println("IgnoreRule can't understand [[:]
		Boolean assume = useJGitRule;
		assertMatch("[[:]"
		assertMatch("[[:]"
	}

	@Test
	public void testUnsupportedGroupCase0() throws Exception {
		assertMatch("[[=a=]]"
		assertMatch("[[=a=]]"
		assertMatch("[=a=]"
		assertMatch("[=a=]"
	}

	@Test
	public void testUnsupportedGroupCase01() throws Exception {
		assertMatch("[.a.]*[.a.]"
	}

	@Test
	public void testUnsupportedGroupCase1() throws Exception {
		assertMatch("[[.a.]]"
		assertMatch("[[.a.]]"
		assertMatch("[.a.]"
		assertMatch("[.a.]"
	}

	@Test
	public void testEscapedBracket1() throws Exception {
		assertMatch("\\["
	}

	@Test
	public void testEscapedBracket2() throws Exception {
		assertMatch("\\[[a]"
	}

	@Test
	public void testEscapedBracket3() throws Exception {
		assertMatch("\\[[a]"
	}

	@Test
	public void testEscapedBracket4() throws Exception {
		assertMatch("\\[[a]"
	}

	@Test
	public void testEscapedBracket5() throws Exception {
		assertMatch("[a\\]]"
	}

	@Test
	public void testEscapedBracket6() throws Exception {
		assertMatch("[a\\]]"
	}

	@Test
	public void testEscapedBackslash() throws Exception {
		if (useJGitRule)
			System.err
					.println("IgnoreRule can't understand escaped backslashes
		Boolean assume = useJGitRule;
		assertMatch("a\\\\b"
	}

	@Test
	public void testMultipleEscapedCharacters1() throws Exception {
		assertMatch("\\]a?c\\*\\[d\\?\\]"
	}

	@Test
	public void testFilePathSimpleCase() throws Exception {
		assertFileNameMatch("a/b"
	}

	@Test
	public void testFilePathCase0() throws Exception {
		assertFileNameMatch("a*b"
	}

	@Test
	public void testFilePathCase1() throws Exception {
		assertFileNameMatch("a?b"
	}

	@Test
	public void testFilePathCase2() throws Exception {
		assertFileNameMatch("a*b"
	}

	@Test
	public void testFilePathCase3() throws Exception {
		assertFileNameMatch("a?b"
	}

}

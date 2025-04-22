
package org.eclipse.jgit.fnmatch;

import static org.eclipse.jgit.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.junit.Test;

public class FileNameMatcherTest {

	private static void assertMatch(final String pattern
			final boolean matchExpected
			throws InvalidPatternException {
		final FileNameMatcher matcher = new FileNameMatcher(pattern
		matcher.append(input);
		assertEquals(matchExpected
		assertEquals(appendCanMatchExpected
	}

	private static void assertFileNameMatch(final String pattern
			final String input
			final char excludedCharacter
			final boolean appendCanMatchExpected)
			throws InvalidPatternException {
		final FileNameMatcher matcher = new FileNameMatcher(pattern
				Character.valueOf(excludedCharacter));
		matcher.append(input);
		assertEquals(matchExpected
		assertEquals(appendCanMatchExpected
	}

	@Test
	public void testVerySimplePatternCase0() throws Exception {
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
	public void testVerySimpleWirdcardCase0() throws Exception {
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
	public void testWordroupCase0() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordroupCase1() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordroupCase2() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordroupCase3() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordroupCase4() throws Exception {
		assertMatch("[[:word:]]"
	}

	@Test
	public void testWordroupCase5() throws Exception {
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
		try {
			assertMatch("[[:]"
			fail("InvalidPatternException expected");
		} catch (InvalidPatternException e) {
		}
	}

	@Test
	public void testUnsupportedGroupCase0() throws Exception {
		try {
			assertMatch("[[=a=]]"
			fail("InvalidPatternException expected");
		} catch (InvalidPatternException e) {
			assertTrue(e.getMessage().contains("[=a=]"));
		}
	}

	@Test
	public void testUnsupportedGroupCase1() throws Exception {
		try {
			assertMatch("[[.a.]]"
			fail("InvalidPatternException expected");
		} catch (InvalidPatternException e) {
			assertTrue(e.getMessage().contains("[.a.]"));
		}
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

	@Test
	public void testReset() throws Exception {
		final String pattern = "helloworld";
		final FileNameMatcher matcher = new FileNameMatcher(pattern
		matcher.append("helloworld");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		matcher.reset();
		matcher.append("hello");
		assertFalse(matcher.isMatch());
		assertTrue(matcher.canAppendMatch());
		matcher.append("world");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		matcher.append("to much");
		assertFalse(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		matcher.reset();
		matcher.append("helloworld");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
	}

	@Test
	public void testCreateMatcherForSuffix() throws Exception {
		final String pattern = "helloworld";
		final FileNameMatcher matcher = new FileNameMatcher(pattern
		matcher.append("hello");
		final FileNameMatcher childMatcher = matcher.createMatcherForSuffix();
		assertFalse(matcher.isMatch());
		assertTrue(matcher.canAppendMatch());
		assertFalse(childMatcher.isMatch());
		assertTrue(childMatcher.canAppendMatch());
		matcher.append("world");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		assertFalse(childMatcher.isMatch());
		assertTrue(childMatcher.canAppendMatch());
		childMatcher.append("world");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		assertTrue(childMatcher.isMatch());
		assertFalse(childMatcher.canAppendMatch());
		childMatcher.reset();
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		assertFalse(childMatcher.isMatch());
		assertTrue(childMatcher.canAppendMatch());
		childMatcher.append("world");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		assertTrue(childMatcher.isMatch());
		assertFalse(childMatcher.canAppendMatch());
	}

	@Test
	public void testCopyConstructor() throws Exception {
		final String pattern = "helloworld";
		final FileNameMatcher matcher = new FileNameMatcher(pattern
		matcher.append("hello");
		final FileNameMatcher copy = new FileNameMatcher(matcher);
		assertFalse(matcher.isMatch());
		assertTrue(matcher.canAppendMatch());
		assertFalse(copy.isMatch());
		assertTrue(copy.canAppendMatch());
		matcher.append("world");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		assertFalse(copy.isMatch());
		assertTrue(copy.canAppendMatch());
		copy.append("world");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		assertTrue(copy.isMatch());
		assertFalse(copy.canAppendMatch());
		copy.reset();
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		assertFalse(copy.isMatch());
		assertTrue(copy.canAppendMatch());
		copy.append("helloworld");
		assertTrue(matcher.isMatch());
		assertFalse(matcher.canAppendMatch());
		assertTrue(copy.isMatch());
		assertFalse(copy.canAppendMatch());
	}
}


package org.eclipse.ui.tests.activities;

import java.util.regex.Pattern;

import org.eclipse.ui.internal.activities.PatternUtil;

import junit.framework.TestCase;

public class PatternUtilTest extends TestCase {
	
    public static void testQuotePattern() {
    	assertTrue(PatternUtil.quotePattern("abcd").equals("\\Qabcd\\E"));
    	assertTrue(PatternUtil.quotePattern("Test\\Q").equals("\\QTest\\Q\\E"));
		assertTrue(PatternUtil.quotePattern("Test\\Q\\E").equals(
				"\\QTest\\Q\\E\\\\E\\Q\\E"));
		assertTrue(PatternUtil.quotePattern("Test\\E\\Q").equals(
				"\\QTest\\E\\\\E\\Q\\Q\\E"));		
		assertTrue(PatternUtil.quotePattern("\\ETest\\E\\\\E\\E").equals(
				"\\Q\\E\\\\E\\QTest\\E\\\\E\\Q\\\\E\\\\E\\Q\\E\\\\E\\Q\\E"));
		String searchString = "xy[^a]";
		assertFalse(Pattern.compile(searchString).matcher(searchString)
				.matches());
		assertTrue(Pattern.compile(PatternUtil.quotePattern(searchString))
				.matcher(searchString).matches());    	
    }
    
    public static void main(String[] args) {
    	testQuotePattern();
    }
}

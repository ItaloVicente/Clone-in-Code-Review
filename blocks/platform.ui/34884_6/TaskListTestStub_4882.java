
package org.eclipse.ui.tests.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.eclipse.ui.dialogs.SearchPattern;

public class SearchPatternAuto extends TestCase {
	
	private static ArrayList resources = new ArrayList();
	
	
	static {
		
		generateRescourcesTestCases('A', 'C', 8, "");
		
		generateRescourcesTestCases('A', 'C', 4, "");
		
	}
	public SearchPatternAuto(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	private static void generateRescourcesTestCases(char startChar, char endChar, int lenght, String resource){
		for (char ch = startChar; ch <= endChar; ch++) {
			String res = resource + String.valueOf(ch);
			if (lenght == res.length()) 
				resources.add(res);
			else if ((res.trim().length() % 2) == 0)
					generateRescourcesTestCases(Character.toUpperCase((char)(startChar + 1)), Character.toUpperCase((char)(endChar + 1)), lenght, res);
				else 
					generateRescourcesTestCases(Character.toLowerCase((char)(startChar + 1)), Character.toLowerCase((char)(endChar + 1)), lenght, res);
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testExactMatch1() {
		String patternText = "abcd ";
		Pattern pattern = Pattern.compile("abcd", Pattern.CASE_INSENSITIVE);
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_EXACT_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
	}
	
	public void testExactMatch2() {
		String patternText = "abcdefgh<";
		Pattern pattern = Pattern.compile("abcdefgh", Pattern.CASE_INSENSITIVE);
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_EXACT_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
	}
	
	public void testPrefixMatch() {
		String patternText = "ab";
		Pattern pattern = Pattern.compile("ab.*", Pattern.CASE_INSENSITIVE);
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_PREFIX_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
	}
	
	public void testPatternMatch1() {
		String patternText = "**cDe";
		Pattern pattern = Pattern.compile(".*cde.*", Pattern.CASE_INSENSITIVE);
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_PATTERN_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
	}
	
	public void testPatternMatch2() {
		String patternText = "**c*e*i";
		Pattern pattern = Pattern.compile(".*c.*e.*i.*", Pattern.CASE_INSENSITIVE);
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_PATTERN_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
	}
	
	public void testCamelCaseMatch1() {
		String patternText = "CD";
		Pattern pattern = Pattern.compile("C[^A-Z]*D.*");
		Pattern pattern2 = Pattern.compile("CD.*", Pattern.CASE_INSENSITIVE);
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_CAMELCASE_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			if (patternMatcher.matches(res) != pattern.matcher(res).matches()) {
				assertEquals(patternMatcher.matches(res), pattern2.matcher(res).matches());
			}
		}
	}
	
	public void testCamelCaseMatch2() {
		String patternText = "AbCd ";
		Pattern pattern = Pattern.compile("Ab[^A-Z]*Cd[^A-Z]*");
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_CAMELCASE_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
	}
	
	public void testCamelCaseMatch3() {
		String patternText = "AbCdE<";
		Pattern pattern = Pattern.compile("Ab[^A-Z]*Cd[^A-Z]*E[^A-Z]*");
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_CAMELCASE_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
	}
	
	public void testBlankMatch() {
		String patternText = "";
		Pattern pattern = Pattern.compile(".*", Pattern.CASE_INSENSITIVE);
		SearchPattern patternMatcher = new SearchPattern();
		patternMatcher.setPattern(patternText);
		assertEquals(patternMatcher.getMatchRule(), SearchPattern.RULE_BLANK_MATCH);
		for (Iterator iter = resources.iterator(); iter.hasNext();) {
			String res = (String) iter.next();
			assertEquals(patternMatcher.matches(res), pattern.matcher(res).matches());
		}
	}
	
}

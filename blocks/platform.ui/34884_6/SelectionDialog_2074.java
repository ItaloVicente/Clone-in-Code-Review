package org.eclipse.ui.dialogs;

import org.eclipse.jface.util.Util;
import org.eclipse.ui.internal.misc.StringMatcher;

public class SearchPattern {

	public static final int RULE_EXACT_MATCH = 0;

	public static final int RULE_PREFIX_MATCH = 0x0001;

	public static final int RULE_PATTERN_MATCH = 0x0002;

	public static final int RULE_CASE_SENSITIVE = 0x0008;

	public static final int RULE_BLANK_MATCH = 0x0020;

	public static final int RULE_CAMELCASE_MATCH = 0x0080;

	private int matchRule;

	private String stringPattern;

	private String initialPattern;

	private StringMatcher stringMatcher;

	private static final char END_SYMBOL = '<';

	private static final char ANY_STRING = '*';

	private static final char BLANK = ' ';

	private int allowedRules;

	public SearchPattern() {
		this(RULE_EXACT_MATCH | RULE_PREFIX_MATCH | RULE_PATTERN_MATCH
				| RULE_CAMELCASE_MATCH | RULE_BLANK_MATCH);
	}

	public SearchPattern(int allowedRules) {
		this.allowedRules = allowedRules;
	}

	public String getPattern() {
		return this.stringPattern;
	}

	public void setPattern(String stringPattern) {
		this.initialPattern = stringPattern;
		this.stringPattern = stringPattern;
		initializePatternAndMatchRule(stringPattern);
		matchRule = matchRule & this.allowedRules;
		if (matchRule == RULE_PATTERN_MATCH) {
			stringMatcher = new StringMatcher(this.stringPattern, true, false);
		}
	}

	public boolean matches(String text) {
		switch (matchRule) {
		case RULE_BLANK_MATCH:
			return true;
		case RULE_PATTERN_MATCH:
			return stringMatcher.match(text);
		case RULE_EXACT_MATCH:
			return stringPattern.equalsIgnoreCase(text);
		case RULE_CAMELCASE_MATCH:
			if (camelCaseMatch(stringPattern, text)) {
				return true;
			}
			default:
			return startsWithIgnoreCase(text, stringPattern);
		}
	}

	private void initializePatternAndMatchRule(String pattern) {
		int length = pattern.length();
		if (length == 0) {
			matchRule = RULE_BLANK_MATCH;
			stringPattern = pattern;
			return;
		}
		char last = pattern.charAt(length - 1);

		if (pattern.indexOf('*') != -1 || pattern.indexOf('?') != -1) {
			matchRule = RULE_PATTERN_MATCH;
			switch (last) {
			case END_SYMBOL:
			case BLANK:
				stringPattern = pattern.substring(0, length - 1);
				break;
			case ANY_STRING:
				stringPattern = pattern;
				break;
			default:
				stringPattern = pattern + ANY_STRING;
			}
			return;
		}

		if (validateMatchRule(pattern, RULE_CAMELCASE_MATCH) == RULE_CAMELCASE_MATCH) {
			matchRule = RULE_CAMELCASE_MATCH;
			stringPattern = pattern;
			return;
		}
		
		if (last == END_SYMBOL || last == BLANK) {
			matchRule = RULE_EXACT_MATCH;
			stringPattern = pattern.substring(0, length - 1);
			return;
		}

		matchRule = RULE_PREFIX_MATCH;
		stringPattern = pattern;

	}

	private boolean startsWithIgnoreCase(String text, String prefix) {
		int textLength = text.length();
		int prefixLength = prefix.length();
		if (textLength < prefixLength)
			return false;
		for (int i = prefixLength - 1; i >= 0; i--) {
			if (Character.toLowerCase(prefix.charAt(i)) != Character
					.toLowerCase(text.charAt(i)))
				return false;
		}
		return true;
	}

	private boolean camelCaseMatch(String pattern, String name) {
		if (pattern == null)
			return true; // null pattern is equivalent to '*'
		if (name == null)
			return false; // null name cannot match

		return camelCaseMatch(pattern, 0, pattern.length(), name, 0, name
				.length());
	}

	private boolean camelCaseMatch(String pattern, int patternStart,
			int patternEnd, String name, int nameStart, int nameEnd) {
		if (name == null)
			return false; // null name cannot match
		if (pattern == null)
			return true; // null pattern is equivalent to '*'
		if (patternEnd < 0)
			patternEnd = pattern.length();
		if (nameEnd < 0)
			nameEnd = name.length();

		if (patternEnd <= patternStart)
			return nameEnd <= nameStart;
		if (nameEnd <= nameStart)
			return false;
		if (name.charAt(nameStart) != pattern.charAt(patternStart)) {
			return false;
		}

		int patternLength = patternEnd;
		
		if (pattern.charAt(patternEnd - 1) == END_SYMBOL || pattern.charAt(patternEnd - 1) == BLANK )
			patternLength = patternEnd - 1;


		char patternChar, nameChar;
		int iPattern = patternStart;
		int iName = nameStart;

		while (true) {

			iPattern++;
			iName++;

				if (iPattern == patternEnd) {
				return true;
			}

			if (iName == nameEnd) {
				if (iPattern == patternLength)
					return true;
				return false;
			}

			if ((patternChar = pattern.charAt(iPattern)) == name.charAt(iName)) {
				continue;
			}

			if (!isPatternCharAllowed(patternChar))
				return false;

			while (true) {
				if (iName == nameEnd) {
					if ((iPattern == patternLength) && (patternChar == END_SYMBOL || patternChar == BLANK))
						return true;
					return false;
				}

				nameChar = name.charAt(iName);

				if ((iPattern == patternLength) && (patternChar == END_SYMBOL || patternChar == BLANK)) {
					if (isNameCharAllowed(nameChar)) {
						return false;
					}
					iName++;
					continue;
				}

				if (Character.isDigit(nameChar)) {
					if (patternChar == nameChar) break;
					iName++;
				} else if (!isNameCharAllowed(nameChar)) {
					iName++;
				} else if (patternChar != nameChar) {
					return false;
				} else {
					break;
				}
			}
		}
	}

	protected boolean isPatternCharAllowed(char patternChar) {
		return patternChar == END_SYMBOL || patternChar == BLANK
			|| Character.isUpperCase(patternChar) || Character.isDigit(patternChar);
	}

	protected boolean isNameCharAllowed(char nameChar) {
		return Character.isUpperCase(nameChar);
	}

	public final int getMatchRule() {
		return this.matchRule;
	}

	private int validateMatchRule(String stringPattern, int matchRule) {

		int starIndex = stringPattern.indexOf('*');
		int questionIndex = stringPattern.indexOf('?');
		if (starIndex < 0 && questionIndex < 0) {
			matchRule &= ~RULE_PATTERN_MATCH;
		} else {
			matchRule |= RULE_PATTERN_MATCH;
		}
		if ((matchRule & RULE_PATTERN_MATCH) != 0) {
			matchRule &= ~RULE_CAMELCASE_MATCH;
			matchRule &= ~RULE_PREFIX_MATCH;
		}

		if ((matchRule & RULE_CAMELCASE_MATCH) != 0) {
			int length = stringPattern.length();
			boolean validCamelCase = true;
			for (int i = 0; i < length && validCamelCase; i++) {
				char ch = stringPattern.charAt(i);
				validCamelCase = isValidCamelCaseChar(ch);
			}
			validCamelCase = validCamelCase && Character.isUpperCase(stringPattern.charAt(0));
			if (validCamelCase) {
				if ((matchRule & RULE_PREFIX_MATCH) != 0) {
					if ((matchRule & RULE_CASE_SENSITIVE) != 0) {
						matchRule &= ~RULE_PREFIX_MATCH;
						matchRule &= ~RULE_CASE_SENSITIVE;
					}
				}
			} else {
				matchRule &= ~RULE_CAMELCASE_MATCH;
				if ((matchRule & RULE_PREFIX_MATCH) == 0) {
					matchRule |= RULE_PREFIX_MATCH;
					matchRule |= RULE_CASE_SENSITIVE;
				}
			}
		}
		return matchRule;
	}

	protected boolean isValidCamelCaseChar(char ch) {
		return true;
	}

	public boolean equalsPattern(SearchPattern pattern) {
		return trimWildcardCharacters(pattern.initialPattern).equals(
				trimWildcardCharacters(this.initialPattern));
	}

	public boolean isSubPattern(SearchPattern pattern) {
		return trimWildcardCharacters(pattern.initialPattern).startsWith(
				trimWildcardCharacters(this.initialPattern));
	}

	private String trimWildcardCharacters(String pattern) {
		return Util.replaceAll(pattern, "\\*+", "\\*"); //$NON-NLS-1$ //$NON-NLS-2$		}
	}

}

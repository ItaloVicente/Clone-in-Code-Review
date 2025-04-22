package org.eclipse.jgit.ignore.internal;

import static java.lang.Character.isLetter;

import java.util.*;
import java.util.regex.Pattern;

import org.eclipse.jgit.errors.InvalidPatternException;

public class Strings {

	static char getPathSeparator(Character pathSeparator) {
		return pathSeparator == null ? '/' : pathSeparator.charValue();
	}

	public static String stripTrailing(String pattern
		while(pattern.length() > 0 && pattern.charAt(pattern.length() - 1) == c){
			pattern = pattern.substring(0
		}
		return pattern;
	}

	private static int count(String s
		int start = 0;
		int count = 0;
		while (true) {
			start = s.indexOf(c
			if(start == -1) {
				break;
			}
			if(!ignoreFirstLast || (start != 0 && start != s.length())) {
				count ++;
			}
			start ++;
		}
		return count;
	}

	public static List<String> split(String pattern
		int count = count(pattern
		if(count < 1){
			throw new IllegalStateException(
		}
		List<String> segments = new ArrayList<String>(count);
		int right = 0;
		while (true) {
			int left = right;
			right = pattern.indexOf(slash
			if(right == -1) {
				if(left < pattern.length()){
					segments.add(pattern.substring(left));
				}
				break;
			}
			if(right - left > 0) {
				if(left == 1){
					segments.add(pattern.substring(left - 1
				} else if(right == pattern.length() - 1){
					segments.add(pattern.substring(left
				} else {
					segments.add(pattern.substring(left
				}
			}
			right ++;
		}
		return segments;
	}

	static boolean isWildCard(String pattern) {
		return pattern.indexOf('*') != -1
				|| pattern.indexOf('?') != -1
				|| pattern.indexOf('[') != -1
				|| pattern.indexOf('\\') != -1
				|| pattern.indexOf(']') != -1;
	}

	final static List<String> POSIX_CHAR_CLASSES = Arrays.asList(
			"alnum"
			"digit"
			"punct"
			);

	final static List<String> JAVA_CHAR_CLASSES = Arrays.asList(
			"\\p{Alnum}"
					"\\p{javaDigit}"
					"\\p{Punct}"
			);

	final static Pattern UNSUPPORTED = Pattern

	static Pattern convertGlob(String pattern) throws InvalidPatternException {
		if (UNSUPPORTED.matcher(pattern).find()) {
			throw new InvalidPatternException(
					"Collating symbols [[.a.]] or equivalence class expressions [[=a=]] are not supported"
					pattern);
		}

		StringBuilder sb = new StringBuilder(pattern.length());

		int in_brackets = 0;
		boolean seenEscape = false;
		boolean ignoreLastBracket = false;
		boolean in_char_class = false;
		char[] charClass = new char[6];

		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			switch (c) {

			case '*':
				if(seenEscape || in_brackets > 0) {
					sb.append(c);
				} else {
					sb.append('.').append(c);
				}
				break;

			case '.':
				if (seenEscape) {
					sb.append(c);
				} else {
					sb.append('\\').append('.');
				}
				break;

			case '?':
				if(seenEscape || in_brackets > 0) {
					sb.append(c);
				} else {
					sb.append('.');
				}
				break;

			case ':':
				if(in_brackets > 0) {
					if(lookBehind(sb) == '[' && isLetter(lookAhead(pattern
						in_char_class = true;
					}
				}
				sb.append(':');
				break;

			case '-':
				if(in_brackets > 0) {
					if(lookAhead(pattern
						sb.append('\\').append(c);
					} else {
						sb.append(c);
					}
				} else {
					sb.append('-');
				}
				break;

			case '\\':
				if(in_brackets > 0) {
					char lookAhead = lookAhead(pattern
					if (lookAhead == ']' || lookAhead == '[') {
						ignoreLastBracket = true;
					}
				}
				sb.append(c);
				break;

			case '[':
				if(in_brackets > 0) {
					sb.append('\\').append('[');
					ignoreLastBracket = true;
				} else {
					if(!seenEscape){
						in_brackets ++;
						ignoreLastBracket = false;
					}
					sb.append('[');
				}
				break;

			case ']':
				if(seenEscape){
					sb.append(']');
					ignoreLastBracket = true;
					break;
				}
				if(in_brackets <= 0) {
					sb.append('\\').append(']');
					ignoreLastBracket = true;
					break;
				}
				char lookBehind = lookBehind(sb);
				if((lookBehind == '[' && !ignoreLastBracket)
						|| lookBehind == '^') {
					sb.append('\\');
					sb.append(']');
					ignoreLastBracket = true;
				} else {
					ignoreLastBracket = false;
					if(!in_char_class) {
						in_brackets --;
						sb.append(']');
					} else {
						in_char_class = false;
						String charCl = checkPosixCharClass(charClass);
						if(charCl != null){
							sb.setLength(sb.length() - 4);
							sb.append(charCl);
						}
						reset(charClass);
					}
				}
				break;

			case '!':
				if(in_brackets > 0) {
					if(lookBehind(sb) == '[') {
						sb.append('^');
					} else {
						sb.append(c);
					}
				} else {
					sb.append(c);
				}
				break;

			default:
				if(in_char_class){
					setNext(charClass
				} else{
					sb.append(c);
				}
				break;

			seenEscape = c == '\\';


		if(in_brackets > 0){
			throw new InvalidPatternException("Not closed bracket?"
		}
		return Pattern.compile(sb.toString());
	}

	private static char lookBehind(StringBuilder buffer) {
		return buffer.length() > 0 ? buffer.charAt(buffer.length() - 1) : 0;
	}

	private static char lookAhead(String pattern
		int idx = i + 1;
		return idx >= pattern.length()? 0 : pattern.charAt(idx);
	}

	private static void setNext(char[] buffer
		for (int i = 0; i < buffer.length; i++) {
			if(buffer[i] == 0){
				buffer[i] = c;
				break;
			}
		}
	}

	private static void reset(char[] buffer){
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = 0;
		}
	}

	private static String checkPosixCharClass(char[] buffer){
		for (int i = 0; i < POSIX_CHAR_CLASSES.size(); i++) {
			String clazz = POSIX_CHAR_CLASSES.get(i);
			boolean match = true;
			for (int j = 0; j < clazz.length(); j++) {
				if(buffer[j] != clazz.charAt(j)){
					match = false;
					break;
				}
			}
			if(match) {
				return JAVA_CHAR_CLASSES.get(i);
			}
		}
		return null;
	}

}

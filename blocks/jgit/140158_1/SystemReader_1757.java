
package org.eclipse.jgit.util;

import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.jgit.internal.JGitText;

public final class StringUtils {
	private static final char[] LC;

	static {
		LC = new char['Z' + 1];
		for (char c = 0; c < LC.length; c++)
			LC[c] = c;
		for (char c = 'A'; c <= 'Z'; c++)
			LC[c] = (char) ('a' + (c - 'A'));
	}

	public static char toLowerCase(char c) {
		return c <= 'Z' ? LC[c] : c;
	}

	public static String toLowerCase(String in) {
		final StringBuilder r = new StringBuilder(in.length());
		for (int i = 0; i < in.length(); i++)
			r.append(toLowerCase(in.charAt(i)));
		return r.toString();
	}


	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	public static boolean equalsIgnoreCase(String a
		if (a == b)
			return true;
		if (a.length() != b.length())
			return false;
		for (int i = 0; i < a.length(); i++) {
			if (toLowerCase(a.charAt(i)) != toLowerCase(b.charAt(i)))
				return false;
		}
		return true;
	}

	public static int compareIgnoreCase(String a
		for (int i = 0; i < a.length() && i < b.length(); i++) {
			int d = toLowerCase(a.charAt(i)) - toLowerCase(b.charAt(i));
			if (d != 0)
				return d;
		}
		return a.length() - b.length();
	}

	public static int compareWithCase(String a
		for (int i = 0; i < a.length() && i < b.length(); i++) {
			int d = a.charAt(i) - b.charAt(i);
			if (d != 0)
				return d;
		}
		return a.length() - b.length();
	}

	public static boolean toBoolean(String stringValue) {
		if (stringValue == null)
			throw new NullPointerException(JGitText.get().expectedBooleanStringValue);

		final Boolean bool = toBooleanOrNull(stringValue);
		if (bool == null)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().notABoolean

		return bool.booleanValue();
	}

	public static Boolean toBooleanOrNull(String stringValue) {
		if (stringValue == null)
			return null;

		if (equalsIgnoreCase("yes"
				|| equalsIgnoreCase("true"
				|| equalsIgnoreCase("1"
				|| equalsIgnoreCase("on"
			return Boolean.TRUE;
		else if (equalsIgnoreCase("no"
				|| equalsIgnoreCase("false"
				|| equalsIgnoreCase("0"
				|| equalsIgnoreCase("off"
			return Boolean.FALSE;
		else
			return null;
	}

	public static String join(Collection<String> parts
		return StringUtils.join(parts
	}

	public static String join(Collection<String> parts
			String lastSeparator) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		int lastIndex = parts.size() - 1;
		for (String part : parts) {
			sb.append(part);
			if (i == lastIndex - 1) {
				sb.append(lastSeparator);
			} else if (i != lastIndex) {
				sb.append(separator);
			}
			i++;
		}
		return sb.toString();
	}

	private StringUtils() {
	}

	public static boolean isEmptyOrNull(String stringValue) {
		return stringValue == null || stringValue.length() == 0;
	}

	public static String replaceLineBreaksWithSpace(String in) {
		char[] buf = new char[in.length()];
		int o = 0;
		for (int i = 0; i < buf.length; ++i) {
			char ch = in.charAt(i);
			if (ch == '\r') {
				if (i + 1 < buf.length && in.charAt(i + 1) == '\n') {
					buf[o++] = ' ';
					++i;
				} else
					buf[o++] = ' ';
			} else if (ch == '\n')
				buf[o++] = ' ';
			else
				buf[o++] = ch;
		}
		return new String(buf
	}
}

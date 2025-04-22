package org.eclipse.jgit.internal.transport.sshd.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class HttpParser {

	public static class ParseException extends Exception {

		private static final long serialVersionUID = -1634090143702048640L;

	}

	private HttpParser() {
	}

	public static StatusLine parseStatusLine(String line)
			throws ParseException {
		int firstBlank = line.indexOf(' ');
		if (firstBlank < 0) {
			throw new ParseException();
		}
		int secondBlank = line.indexOf(' '
		if (secondBlank < 0) {
			secondBlank = line.length();
		}
		int resultCode;
		try {
			resultCode = Integer.parseUnsignedInt(
					line.substring(firstBlank + 1
		} catch (NumberFormatException e) {
			throw new ParseException();
		}
		if (secondBlank < line.length()) {
			reason = line.substring(secondBlank + 1);
		}
		return new StatusLine(line.substring(0
				reason);
	}

	public static List<AuthenticationChallenge> getAuthenticationHeaders(
			List<String> reply
		List<AuthenticationChallenge> challenges = new ArrayList<>();
		Iterator<String> lines = reply.iterator();
		lines.next();
		StringBuilder value = null;
		while (lines.hasNext()) {
			String line = lines.next();
			if (line.isEmpty()) {
				break;
			}
			if (Character.isWhitespace(line.charAt(0))) {
				if (value == null) {
					continue;
				}
				int i = skipWhiteSpace(line
				value.append(' ').append(line
				continue;
			}
			if (value != null) {
				parseChallenges(challenges
				value = null;
			}
			int firstColon = line.indexOf(':');
			if (firstColon > 0 && authenticationHeader
					.equalsIgnoreCase(line.substring(0
				value = new StringBuilder(line.substring(firstColon + 1));
			}
		}
		if (value != null) {
			parseChallenges(challenges
		}
		return challenges;
	}

	private static void parseChallenges(
			List<AuthenticationChallenge> challenges
			String header) {
		int length = header.length();
		for (int i = 0; i < length;) {
			int start = skipWhiteSpace(header
			int end = scanToken(header
			if (end <= start) {
				break;
			}
			AuthenticationChallenge challenge = new AuthenticationChallenge(
					header.substring(start
			challenges.add(challenge);
			i = parseChallenge(challenge
		}
	}

	private static int parseChallenge(AuthenticationChallenge challenge
			String header
		int length = header.length();
		boolean first = true;
		for (int start = from; start <= length; first = false) {
			start = skipWhiteSpace(header
			int end = scanToken(header
			if (end == start) {
				if (start < header.length() && header.charAt(start) == '
					return start + 1;
				}
				return start;
			}
			int next = skipWhiteSpace(header
			if (next >= length || header.charAt(next) != '=') {
				if (first) {
					challenge.setToken(header.substring(start
					if (next < length && header.charAt(next) == '
						next++;
					}
					return next;
				} else {
					return start;
				}
			}
			int nextStart = skipWhiteSpace(header
			if (nextStart >= length) {
				if (next == end) {
					challenge.setToken(header.substring(start
				} else {
					challenge.addArgument(header.substring(start
				}
				return nextStart;
			}
			if (nextStart == end + 1 && header.charAt(nextStart) == '=') {
				end = nextStart + 1;
				while (end < length && header.charAt(end) == '=') {
					end++;
				}
				challenge.setToken(header.substring(start
				end = skipWhiteSpace(header
				if (end < length && header.charAt(end) == '
					end++;
				}
				return end;
			}
			if (header.charAt(nextStart) == '
				if (next == end) {
					challenge.setToken(header.substring(start
					return nextStart + 1;
				} else {
					challenge.addArgument(header.substring(start
					start = nextStart + 1;
				}
			} else {
				if (header.charAt(nextStart) == '"') {
					int nextEnd[] = { nextStart + 1 };
					String value = scanQuotedString(header
							nextEnd);
					challenge.addArgument(header.substring(start
					start = nextEnd[0];
				} else {
					int nextEnd = scanToken(header
					challenge.addArgument(header.substring(start
							header.substring(nextStart
					start = nextEnd;
				}
				start = skipWhiteSpace(header
				if (start < length && header.charAt(start) == '
					start++;
				}
			}
		}
		return length;
	}

	private static int skipWhiteSpace(String header
		int length = header.length();
		while (i < length && Character.isWhitespace(header.charAt(i))) {
			i++;
		}
		return i;
	}

	private static int scanToken(String header
		int length = header.length();
		int i = from;
		while (i < length) {
			char c = header.charAt(i);
			switch (c) {
			case '!':
			case '#':
			case '$':
			case '%':
			case '&':
			case '\'':
			case '*':
			case '+':
			case '-':
			case '.':
			case '^':
			case '_':
			case '`':
			case '|':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				i++;
				break;
			default:
				if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
					i++;
					break;
				}
				return i;
			}
		}
		return i;
	}

	private static String scanQuotedString(String header
		StringBuilder result = new StringBuilder();
		int length = header.length();
		boolean quoted = false;
		int i = from;
		while (i < length) {
			char c = header.charAt(i++);
			if (quoted) {
				result.append(c);
				quoted = false;
			} else if (c == '\\') {
				quoted = true;
			} else if (c == '"') {
				break;
			} else {
				result.append(c);
			}
		}
		to[0] = i;
		return result.toString();
	}
}

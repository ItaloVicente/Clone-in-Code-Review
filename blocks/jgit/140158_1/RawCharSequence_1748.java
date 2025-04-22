
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Arrays;

import org.eclipse.jgit.lib.Constants;

public abstract class QuotedString {
	public static final GitPathStyle GIT_PATH = new GitPathStyle();

	public static final BourneStyle BOURNE = new BourneStyle();

	public static final BourneUserPathStyle BOURNE_USER_PATH = new BourneUserPathStyle();

	public abstract String quote(String in);

	public String dequote(String in) {
		final byte[] b = Constants.encode(in);
		return dequote(b
	}

	public abstract String dequote(byte[] in

	public static class BourneStyle extends QuotedString {
		@Override
		public String quote(String in) {
			final StringBuilder r = new StringBuilder();
			r.append('\'');
			int start = 0
			for (; i < in.length(); i++) {
				switch (in.charAt(i)) {
				case '\'':
				case '!':
					r.append(in
					r.append('\'');
					r.append('\\');
					r.append(in.charAt(i));
					r.append('\'');
					start = i + 1;
					break;
				}
			}
			r.append(in
			r.append('\'');
			return r.toString();
		}

		@Override
		public String dequote(byte[] in
			boolean inquote = false;
			final byte[] r = new byte[ie - ip];
			int rPtr = 0;
			while (ip < ie) {
				final byte b = in[ip++];
				switch (b) {
				case '\'':
					inquote = !inquote;
					continue;
				case '\\':
					if (inquote || ip == ie)
					else
						r[rPtr++] = in[ip++];
					continue;
				default:
					r[rPtr++] = b;
					continue;
				}
			}
			return RawParseUtils.decode(UTF_8
		}
	}

	public static class BourneUserPathStyle extends BourneStyle {
		@Override
		public String quote(String in) {
			}

				final int i = in.indexOf('/') + 1;
				if (i == in.length())
					return in;
				return in.substring(0
			}

			return super.quote(in);
		}
	}

	public static final class GitPathStyle extends QuotedString {
		private static final byte[] quote;
		static {
			quote = new byte[128];
			Arrays.fill(quote

			for (int i = '0'; i <= '9'; i++)
				quote[i] = 0;
			for (int i = 'a'; i <= 'z'; i++)
				quote[i] = 0;
			for (int i = 'A'; i <= 'Z'; i++)
				quote[i] = 0;
			quote[' '] = 0;
			quote['$'] = 0;
			quote['%'] = 0;
			quote['&'] = 0;
			quote['*'] = 0;
			quote['+'] = 0;
			quote['
			quote['-'] = 0;
			quote['.'] = 0;
			quote['/'] = 0;
			quote[':'] = 0;
			quote[';'] = 0;
			quote['='] = 0;
			quote['?'] = 0;
			quote['@'] = 0;
			quote['_'] = 0;
			quote['^'] = 0;
			quote['|'] = 0;
			quote['~'] = 0;

			quote['\u0007'] = 'a';
			quote['\b'] = 'b';
			quote['\f'] = 'f';
			quote['\n'] = 'n';
			quote['\r'] = 'r';
			quote['\t'] = 't';
			quote['\u000B'] = 'v';
			quote['\\'] = '\\';
			quote['"'] = '"';
		}

		@Override
		public String quote(String instr) {
			if (instr.length() == 0)
			boolean reuse = true;
			final byte[] in = Constants.encode(instr);
			final StringBuilder r = new StringBuilder(2 + in.length);
			r.append('"');
			for (int i = 0; i < in.length; i++) {
				final int c = in[i] & 0xff;
				if (c < quote.length) {
					final byte style = quote[c];
					if (style == 0) {
						r.append((char) c);
						continue;
					}
					if (style > 0) {
						reuse = false;
						r.append('\\');
						r.append((char) style);
						continue;
					}
				}

				reuse = false;
				r.append('\\');
				r.append((char) (((c >> 6) & 03) + '0'));
				r.append((char) (((c >> 3) & 07) + '0'));
				r.append((char) (((c >> 0) & 07) + '0'));
			}
			if (reuse)
				return instr;
			r.append('"');
			return r.toString();
		}

		@Override
		public String dequote(byte[] in
			if (2 <= inEnd - inPtr && in[inPtr] == '"' && in[inEnd - 1] == '"')
				return dq(in
			return RawParseUtils.decode(UTF_8
		}

		private static String dq(byte[] in
			final byte[] r = new byte[inEnd - inPtr];
			int rPtr = 0;
			while (inPtr < inEnd) {
				final byte b = in[inPtr++];
				if (b != '\\') {
					r[rPtr++] = b;
					continue;
				}

				if (inPtr == inEnd) {
					r[rPtr++] = '\\';
					break;
				}

				switch (in[inPtr++]) {
				case 'a':
					continue;
				case 'b':
					r[rPtr++] = '\b';
					continue;
				case 'f':
					r[rPtr++] = '\f';
					continue;
				case 'n':
					r[rPtr++] = '\n';
					continue;
				case 'r':
					r[rPtr++] = '\r';
					continue;
				case 't':
					r[rPtr++] = '\t';
					continue;
				case 'v':
					continue;

				case '\\':
				case '"':
					r[rPtr++] = in[inPtr - 1];
					continue;

				case '0':
				case '1':
				case '2':
				case '3': {
					int cp = in[inPtr - 1] - '0';
					for (int n = 1; n < 3 && inPtr < inEnd; n++) {
						final byte c = in[inPtr];
						if ('0' <= c && c <= '7') {
							cp <<= 3;
							cp |= c - '0';
							inPtr++;
						} else {
							break;
						}
					}
					r[rPtr++] = (byte) cp;
					continue;
				}

				default:
					r[rPtr++] = '\\';
					r[rPtr++] = in[inPtr - 1];
					continue;
				}
			}

			return RawParseUtils.decode(UTF_8
		}

		private GitPathStyle() {
		}
	}
}

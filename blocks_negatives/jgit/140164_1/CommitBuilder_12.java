			if (ch == '\r') {
				if (i + 1 < in.length() && in.charAt(i + 1) == '\n') {
					out.write('\n');
					out.write(' ');
					++i;
				} else {
					out.write('\n');
					out.write(' ');
				}
			} else if (ch == '\n') {
				out.write('\n');
				out.write(' ');
			} else {
				if (ch > 127)
					throw new IllegalArgumentException(MessageFormat
							.format(JGitText.get().notASCIIString, in));
				out.write(ch);
			}

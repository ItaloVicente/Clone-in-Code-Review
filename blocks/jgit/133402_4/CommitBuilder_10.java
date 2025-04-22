	static void writeGpgSignatureString(String in
			throws IOException
		for (int i = 0; i < in.length(); ++i) {
			char ch = in.charAt(i);
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
							.format(JGitText.get().notASCIIString
				out.write(ch);
			}
		}
	}


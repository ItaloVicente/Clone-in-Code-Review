	/**
	 * Writes signature to output as per <a href=
	 * header</a>.
	 * <p>
	 * CRLF and CR will be sanitized to LF and signature will have a hanging
	 * indent of one space starting with line two. A trailing line break is
	 * <em>not</em> written; the caller is supposed to terminate the GPG
	 * signature header by writing a single newline.
	 * </p>
	 *
	 * @param in
	 *            signature string with line breaks
	 * @param out
	 *            output stream
	 * @throws IOException
	 *             thrown by the output stream
	 * @throws IllegalArgumentException
	 *             if the signature string contains non 7-bit ASCII chars
	 */
	static void writeGpgSignatureString(String in, OutputStream out)
			throws IOException, IllegalArgumentException {
		int length = in.length();
		for (int i = 0; i < length; ++i) {
			char ch = in.charAt(i);
			switch (ch) {
			case '\r':
				if (i + 1 < length && in.charAt(i + 1) == '\n') {
					++i;
				}
				if (i + 1 < length) {
					out.write('\n');
					out.write(' ');
				}
				break;
			case '\n':
				if (i + 1 < length) {
					out.write('\n');
					out.write(' ');
				}
				break;
			default:
				if (ch > 127)
					throw new IllegalArgumentException(MessageFormat
							.format(JGitText.get().notASCIIString, in));
				out.write(ch);
				break;
			}
		}
	}


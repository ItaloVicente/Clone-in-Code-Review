	private int readNulTerminatedString(InputStream in
			throws IOException {
		int n = 0;
		for (;;) {
			int c = in.read();
			if (c < 0) {
				throw new EOFException(JGitText.get().shortReadOfBlock);
			}
			if (c == 0) {
				break;
			}
			out.write(c);
			n++;
		}
		return n;
	}

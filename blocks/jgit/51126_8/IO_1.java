	public static String readLine(Reader in
		if (in.markSupported()) {
			if (sizeHint <= 0) {
				sizeHint = 1024;
			}
			StringBuilder sb = new StringBuilder(sizeHint);
			char[] buf = new char[sizeHint];
			while (true) {
				in.mark(sizeHint);
				int n = in.read(buf);
				if (n < 0) {
					in.reset();
					return sb.toString();
				}
				for (int i = 0; i < n; i++) {
					if (buf[i] == '\n') {
						resetAndSkipFully(in
						sb.append(buf
						return sb.toString();
					}
				}
				if (n > 0) {
					sb.append(buf
				}
				resetAndSkipFully(in
			}
		} else {
			StringBuilder buf = sizeHint > 0
					? new StringBuilder(sizeHint)
					: new StringBuilder();
			int i;
			while ((i = in.read()) != -1) {
				char c = (char) i;
				buf.append(c);
				if (c == '\n') {
					break;
				}
			}
			return buf.toString();
		}
	}

	private static void resetAndSkipFully(Reader fd
		fd.reset();
		while (toSkip > 0) {
			long r = fd.skip(toSkip);
			if (r <= 0) {
				throw new EOFException(JGitText.get().shortSkipOfBlock);
			}
			toSkip -= r;
		}
	}


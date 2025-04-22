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
				for (int i = 0; i < n; i++) {
					if (buf[i] == '\n') {
						in.reset();
						skipFully(in
						sb.append(buf
						return sb.toString();
					}
				}
				if (n > 0) {
					sb.append(buf
				}
				if (n < buf.length) {
					in.reset();
					skipFully(in
					return sb.toString();
				}
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


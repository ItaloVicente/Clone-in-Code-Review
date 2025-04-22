		int overflow = buffer((byte) b);
		if (overflow >= 0)
			return;
		if (isBinary) {
			out.write(b);
			return;
		}
		if (b == '\n') {
			if (buf == '\r') {
				out.write('\n');
				buf = -1;
			} else if (buf == -1) {
				out.write('\r');
				out.write('\n');
				buf = -1;
			}
		} else if (b == '\r') {
			out.write(b);
			buf = '\r';
		} else {
			out.write(b);
			buf = -1;
		}

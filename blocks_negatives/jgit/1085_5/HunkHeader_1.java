		final EditList r = new EditList();
		final byte[] buf = file.buf;
		int c = nextLF(buf, startOffset);
		int oLine = old.startLine;
		int nLine = newStartLine;
		Edit in = null;

		SCAN: for (; c < endOffset; c = nextLF(buf, c)) {
			switch (buf[c]) {
			case ' ':
			case '\n':
				in = null;
				oLine++;
				nLine++;
				continue;

			case '-':
				if (in == null) {
					in = new Edit(oLine - 1, nLine - 1);
					r.add(in);

		if (editList == null) {
			editList = new EditList();
			final byte[] buf = file.buf;
			int c = nextLF(buf
			int oLine = old.startLine;
			int nLine = newStartLine;
			Edit in = null;

			SCAN: for (; c < endOffset; c = nextLF(buf
				switch (buf[c]) {
				case ' ':
				case '\n':
					in = null;
					oLine++;
					nLine++;
					continue;

				case '-':
					if (in == null) {
						in = new Edit(oLine - 1
						editList.add(in);
					}
					oLine++;
					in.extendA();
					continue;

				case '+':
					if (in == null) {
						in = new Edit(oLine - 1
						editList.add(in);
					}
					nLine++;
					in.extendB();
					continue;

					continue;

				default:
					break SCAN;

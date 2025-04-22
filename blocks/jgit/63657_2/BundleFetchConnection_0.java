		StringBuilder line = new StringBuilder();
		boolean done = false;
		while (!done) {
			bin.mark(hdrbuf.length);
			final int cnt = bin.read(hdrbuf);
			int lf = 0;
			while (lf < cnt && hdrbuf[lf] != '\n')
				lf++;
			bin.reset();
			IO.skipFully(bin
			if (lf < cnt && hdrbuf[lf] == '\n') {
				IO.skipFully(bin
				done = true;
			}
			line.append(RawParseUtils.decode(Constants.CHARSET
		}
		return line.toString();

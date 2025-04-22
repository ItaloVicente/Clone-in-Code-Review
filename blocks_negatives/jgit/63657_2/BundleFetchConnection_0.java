		bin.mark(hdrbuf.length);
		final int cnt = bin.read(hdrbuf);
		int lf = 0;
		while (lf < cnt && hdrbuf[lf] != '\n')
			lf++;
		bin.reset();
		IO.skipFully(bin, lf);
		if (lf < cnt && hdrbuf[lf] == '\n')
			IO.skipFully(bin, 1);
		return RawParseUtils.decode(Constants.CHARSET, hdrbuf, 0, lf);

		ptr = footerStart + 3;
		final ArrayList<FooterLine> r = new ArrayList<>(4);
		final Charset enc = guessEncoding();
		while (ptr < pre) {
			final int keyEnd = RawParseUtils.endOfFooterLineKey(raw
			if (keyEnd < 0) {
				ptr = RawParseUtils.nextLF(raw

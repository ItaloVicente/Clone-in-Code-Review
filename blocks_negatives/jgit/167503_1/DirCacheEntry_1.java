		final int actLen = len + pathLen;
		final int expLen = (actLen + 8) & ~7;
		final int padLen = expLen - actLen - skipped;
		if (padLen > 0) {
			IO.skipFully(in, padLen);
			md.update(nullpad, 0, padLen);

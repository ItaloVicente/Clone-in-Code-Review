		if (version == DirCacheVersion.DIRC_VERSION_PATHCOMPRESS) {
			if (skipped == 0) {
				int b = in.read();
				if (b < 0) {
					throw new EOFException(JGitText.get().shortReadOfBlock);
				}
				md.update((byte) b);
			}
		} else {
			final int actLen = len + pathLen;
			final int expLen = (actLen + 8) & ~7;
			final int padLen = expLen - actLen - skipped;
			if (padLen > 0) {
				IO.skipFully(in
				md.update(nullpad
			}

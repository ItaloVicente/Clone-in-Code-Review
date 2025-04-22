	public final String getMessageWithoutFooter() {
		byte[] raw = buffer;
		int msgB = RawParseUtils.commitMessage(raw
		if (msgB < 0) {
		}

		int ptr = raw.length - 1;
		while (raw[ptr] == '\n') {
			ptr--;
		}

		if (ptr < msgB) {
			return "";
		}

		int pre = ptr;
		int footerStart = RawParseUtils.prevDLF(raw
		if (footerStart <= msgB) {
			return RawParseUtils.decode(guessEncoding()
		}

		ptr = footerStart + 3;
		boolean hasFooter = false;
		while (ptr < pre) {
			final int keyEnd = RawParseUtils.endOfFooterLineKey(raw
			if (keyEnd > 0) {
				hasFooter = true;
				break;
			}
			ptr = RawParseUtils.nextLF(raw
		}

		if (hasFooter) {
			return RawParseUtils.decode(guessEncoding()
		} else {
			return RawParseUtils.decode(guessEncoding()
		}
	}



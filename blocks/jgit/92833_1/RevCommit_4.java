	public final String getMessageWithoutFooter() {
		byte[] raw = buffer;
		int msgB = RawParseUtils.commitMessage(raw
		if (msgB < 0) {
		}

		int ptr = raw.length - 1;
			ptr--;

		if (ptr < msgB) {
			return "";
		}

		for (;;) {
			int pre = ptr;
			ptr = RawParseUtils.prevLF(raw
			if (ptr <= msgB) {
				ptr = pre;
				break;
			}

			final int keyStart = ptr + 2;
			if (raw[keyStart] == '\n')

			final int keyEnd = RawParseUtils.endOfFooterLineKey(raw
			if (keyEnd < 0) {
				continue;
			}
		}

		return RawParseUtils.decode(guessEncoding()
	}



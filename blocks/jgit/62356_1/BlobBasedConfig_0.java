		final String decoded;
		if (blob.length >= 3 && blob[0] == (byte) 0xEF
				&& blob[1] == (byte) 0xBB && blob[2] == (byte) 0xBF) {
			decoded = RawParseUtils.decode(RawParseUtils.UTF8_CHARSET
					blob
		} else {
			decoded = RawParseUtils.decode(blob);
		}
		fromText(decoded);

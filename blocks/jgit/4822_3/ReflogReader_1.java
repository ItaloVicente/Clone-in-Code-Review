	public ReflogEntry getReverseEntry(int number) throws IOException {
		if (number < 0)
			throw new IllegalArgumentException();

		final byte[] log;
		try {
			log = IO.readFully(logName);
		} catch (FileNotFoundException e) {
			return null;
		}

		int rs = RawParseUtils.prevLF(log
		int current = 0;
		while (rs >= 0) {
			rs = RawParseUtils.prevLF(log
			if (number == current)
				return new ReflogEntry(log
			current++;
		}
		return null;
	}


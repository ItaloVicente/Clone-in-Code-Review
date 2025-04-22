	void ignoreAllSpace(@SuppressWarnings("unused") boolean on) {
		diffFmt.setRawTextFactory(RawTextIgnoreAllWhitespace.FACTORY);
	}

	@Option(name = "-U"
	void unified(int lines) {
		diffFmt.setContext(lines);
	}

	@Option(name = "--abbrev"
	void abbrev(int lines) {
		diffFmt.setAbbreviationLength(lines);
	}

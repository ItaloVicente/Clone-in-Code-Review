	@Option(name = "--ignore-space-at-eol")
	void ignoreSpaceAtEol(@SuppressWarnings("unused") boolean on) {
		diffFmt.setRawTextFactory(RawTextIgnoreTrailingWhitespace.FACTORY);
	}

	@Option(name = "--ignore-leading-space")
	void ignoreLeadingSpace(@SuppressWarnings("unused") boolean on) {
		diffFmt.setRawTextFactory(RawTextIgnoreLeadingWhitespace.FACTORY);
	}

	@Option(name = "-b"
	void ignoreSpaceChange(@SuppressWarnings("unused") boolean on) {
		diffFmt.setRawTextFactory(RawTextIgnoreWhitespaceChange.FACTORY);
	}

	@Option(name = "-w"
	void ignoreAllSpace(@SuppressWarnings("unused") boolean on) {
		diffFmt.setRawTextFactory(RawTextIgnoreAllWhitespace.FACTORY);
	}

	@Option(name = "--src-prefix"
	void sourcePrefix(String path) {
		diffFmt.setOldPrefix(path);
	}

	@Option(name = "--dst-prefix"
	void dstPrefix(String path) {
		diffFmt.setNewPrefix(path);
	}

	@Option(name = "--no-prefix"
	void noPrefix(@SuppressWarnings("unused") boolean on) {
		diffFmt.setOldPrefix("");
		diffFmt.setNewPrefix("");
	}


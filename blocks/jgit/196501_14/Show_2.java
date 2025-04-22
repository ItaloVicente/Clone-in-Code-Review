	@Option(name = "--text"
	void asText(@SuppressWarnings("unused") boolean on) {
		diffFmt.setAsText(true);
	}

	@Option(name = "--binary"
	void asBinary(@SuppressWarnings("unused") boolean on) {
		diffFmt.setAsBinary(true);
	}


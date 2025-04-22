		return NLS.bind(DESCRIPTION_PATTERN, getName(), filterHint);
	}

	public String getName() {
		return this.name;
	}

	public HistoryPageInput getInputInternal() {
		return this.input;

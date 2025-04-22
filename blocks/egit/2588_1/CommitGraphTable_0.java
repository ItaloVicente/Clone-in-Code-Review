	void setHistoryPageInput(HistoryPageInput input) {
		this.input = input;
		if (menuListener != null)
			menuListener.setInput(input);
	}


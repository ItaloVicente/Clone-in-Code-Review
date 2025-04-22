		setAbbreviationLength(8);
	}

	protected OutputStream getOutputStream() {
		return out;
	}

	public void setRepository(Repository repository) {
		db = repository;

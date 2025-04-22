	public void set(String section
			String value) {
		if (value == null) {
			throw new NullPointerException();
		}

		setStringList(section
				.singletonList(value));
	}


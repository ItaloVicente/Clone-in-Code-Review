	@Override
	public boolean hasObjectMap() throws IOException {
		if (objIndexPosition == -1) {
			readFileFooter();
		}

		return (objPosition > 0 || refEnd == 24);
	}


		return filename.substring(index + 1, filename.length());
	}

	public String getName() {

		int index = filename.lastIndexOf('.');
		if (index == -1) {

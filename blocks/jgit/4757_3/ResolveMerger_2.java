	private FileMode getFileMode() {
		FileMode mode = tw.getFileMode(0);
		if (mode == FileMode.MISSING) {
			mode = tw.getFileMode(1);
			if (mode == FileMode.MISSING) {
				mode = tw.getFileMode(2);
				if (mode == FileMode.MISSING)
					mode = FileMode.REGULAR_FILE;
			}
		}
		return mode;
	}


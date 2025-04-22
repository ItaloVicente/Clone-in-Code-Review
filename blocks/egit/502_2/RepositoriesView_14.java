		String dirString;
		try {
			dirString = file.getCanonicalPath();
		} catch (IOException e) {
			dirString = file.getAbsolutePath();
		}

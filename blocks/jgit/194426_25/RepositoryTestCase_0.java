		checkInputStream(new FileInputStream(f)
	}

	protected static void checkInputStream(InputStream is
			throws IOException {
		try (Reader r = new InputStreamReader(is

	public static StringBuilder readToBuilder(String fileName) throws IOException {
		try (FileReader reader = new FileReader(fileName)) {
			StringBuilder result = readToBuilder(reader);
			return result;
		}
	}

	@Deprecated

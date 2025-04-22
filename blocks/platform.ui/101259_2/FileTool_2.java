
	public static void writeFromBuilder(String fileName, StringBuilder content) throws IOException {
		try (Writer writer = new FileWriter(fileName)) {
			writer.write(content.toString());
		}
	}

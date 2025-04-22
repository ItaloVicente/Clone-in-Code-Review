	private void writeToFile(final String fileName, String content)
			throws IOException {
		Writer output = new BufferedWriter(new FileWriter(fileName));
		try {
			output.write(content);
		} finally {
			output.close();
		}
	}



	private InputStream writeToString(String... rules) throws IOException {
		StringBuilder data = new StringBuilder();
		for (String line : rules) {
			data.append(line + "\n");
		}
		return new ByteArrayInputStream(data.toString().getBytes("UTF-8"));
	}

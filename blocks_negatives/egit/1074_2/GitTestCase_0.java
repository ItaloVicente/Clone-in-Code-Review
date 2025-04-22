	protected String slurpAndClose(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			int ch;
			while ((ch = inputStream.read()) != -1) {
				stringBuilder.append((char)ch);
			}
		} finally {
			inputStream.close();
		}
		return stringBuilder.toString();
	}


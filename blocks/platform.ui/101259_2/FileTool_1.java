	public static StringBuilder readToBuilder(Reader reader) throws IOException {
		StringBuilder s = new StringBuilder();
		try {
			char[] buffer = new char[8196];
			int chars = reader.read(buffer);
			while (chars != -1) {
				s.append(buffer, 0, chars);
				chars = reader.read(buffer);
			}
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return s;
	}

	@Deprecated

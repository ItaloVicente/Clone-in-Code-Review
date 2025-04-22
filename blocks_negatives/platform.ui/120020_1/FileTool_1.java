	/**
	 * @deprecated Use {@link FileTool#readToBuilder(Reader)} instead.
	 */
	@Deprecated
	public static StringBuffer read(Reader reader) throws IOException {
		StringBuffer s = new StringBuffer();
		try {
			char[] buffer= new char[8196];
			int chars= reader.read(buffer);
			while (chars != -1) {
				s.append(buffer, 0, chars);
				chars= reader.read(buffer);
			}
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return s;
	}


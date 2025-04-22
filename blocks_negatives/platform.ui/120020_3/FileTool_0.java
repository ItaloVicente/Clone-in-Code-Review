	/**
	 * @deprecated Use {@link FileTool#readToBuilder(String)} instead.
	 */
	@Deprecated
	public static StringBuffer read(String fileName) throws IOException {
		try (FileReader reader = new FileReader(fileName)) {
			StringBuffer result = read(reader);
			return result;
		}
	}


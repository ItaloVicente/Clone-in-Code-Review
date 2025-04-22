	/**
	 * @deprecated Use {@link FileTool#writeFromBuilder(String, StringBuilder)}
	 *             instead.
	 */
	@Deprecated
	public static void write(String fileName, StringBuffer content) throws IOException {
		try (Writer writer = new FileWriter(fileName)) {
			writer.write(content.toString());
		}
	}


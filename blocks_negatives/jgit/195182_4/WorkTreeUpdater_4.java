	 * @param resultStreamLoader with the content to be updated
	 * @param streamType         for parsing the content
	 * @param smudgeCommand      for formatting the content
	 * @param path               of the file to be updated
	 * @param file               to be updated
	 * @param safeWrite          whether the content should be written to a buffer first
	 * @throws IOException if the {@link CheckoutMetadata} cannot be determined
	 */
	public void updateFileWithContent(
			StreamLoader resultStreamLoader,
			EolStreamType streamType,
			String smudgeCommand,
			String path,
			File file,
			boolean safeWrite)
			throws IOException {

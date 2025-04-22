	 * @param path          of the file to be deleted
	 * @param file          to be deleted
	 * @param streamType    to use for cleanup metadata
	 * @param smudgeCommand to use for cleanup metadata
	 * @throws IOException if the file cannot be deleted
	 */
	public void deleteFile(String path, File file, EolStreamType streamType, String smudgeCommand)
			throws IOException {

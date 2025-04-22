	/**
	 * Parse files according to gitignore standards.
	 *
	 * @throws IOException
	 * 			  Error thrown when reading an ignore file.
	 */
	private void parse() throws IOException {
		if (secondaryFile != null && secondaryFile.exists())
			parse(secondaryFile);

		parse(new File(baseDir.getAbsolutePath(), ".gitignore"));

	private void parse() throws IOException {
		if (secondaryFile != null && secondaryFile.exists())
			parse(secondaryFile);

		parse(new File(baseDir.getAbsolutePath(), ".gitignore"));
	}

	private void parse(File targetFile) throws IOException {
		if (!targetFile.exists())
			return;

		BufferedReader br = new BufferedReader(new FileReader(targetFile));

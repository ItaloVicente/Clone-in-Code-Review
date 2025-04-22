	public boolean isIgnored(String target) throws IOException {
		matched = false;
		File targetFile = new File(target);
		String tar = baseDir.toURI().relativize(targetFile.toURI()).getPath();

		if (tar.length() == target.length())
			return false;

		if (rules.isEmpty()) {
			parse();
		}

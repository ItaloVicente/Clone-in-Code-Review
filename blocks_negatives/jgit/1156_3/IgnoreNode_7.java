	public boolean isIgnored(String target) throws IOException {
		matched = false;
		File targetFile = new File(target);
		String tar = baseDir.toURI().relativize(targetFile.toURI()).getPath();

		if (tar.length() == target.length())
			return false;

		if (rules.isEmpty()) {
			parse();
		}
		if (rules.isEmpty())
			return false;

		/*
		 * Boolean matched is necessary because we may have encountered
		 * a negation ("!/test.c").
		 */

		int i;
		for (i = rules.size() -1; i > -1; i--) {
			matched = rules.get(i).isMatch(tar, targetFile.isDirectory());
			if (matched)
				break;

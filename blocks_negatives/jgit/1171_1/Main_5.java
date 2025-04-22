	private static File findGitDir() {
		Set<String> ceilingDirectories = new HashSet<String>();
		String ceilingDirectoriesVar = SystemReader.getInstance().getenv(
				Constants.GIT_CEILING_DIRECTORIES_KEY);
		if (ceilingDirectoriesVar != null) {
			ceilingDirectories.addAll(Arrays.asList(ceilingDirectoriesVar
					.split(File.pathSeparator)));
		}
		File current = new File("").getAbsoluteFile();
		while (current != null) {
			final File gitDir = new File(current, Constants.DOT_GIT);
			if (gitDir.isDirectory())
				return gitDir;
			current = current.getParentFile();
			if (current != null
					&& ceilingDirectories.contains(current.getPath()))
				break;
		}
		return null;
	}


	private Set<String> cleanPath(String path
			Set<String> inFiles) throws IOException {
		int mode;
		String tail;
		File curFile = new File(repo.getWorkTree()
		boolean isGitRepo = (curFile.isDirectory()
				&& new File(curFile
		if (recurse || isGitRepo) {
			mode = FileUtils.RECURSIVE;
		} else {
			mode = FileUtils.NONE;
		}

		if (isGitRepo) {
			if (force) {
				if (!dryRun) {
					FileUtils.delete(curFile
				}
				inFiles.add(path + tail);
			}
		} else {
			if (!dryRun) {
				FileUtils.delete(curFile
			}
			inFiles.add(path + tail);
		}

		return inFiles;
	}


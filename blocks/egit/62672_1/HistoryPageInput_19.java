
	private boolean inGitDir(Repository repository, File file) {
		return file.getAbsoluteFile().toPath().startsWith(
				repository.getDirectory().getAbsoluteFile().toPath());
	}

	private List<File> filterFilesInGitDir(Repository repository,
			File[] fileItems) {
		List<File> result = new ArrayList<>(fileItems.length);
		Path gitDirPath = repository.getDirectory().getAbsoluteFile().toPath();
		for (File f : fileItems) {
			if (!f.getAbsoluteFile().toPath().startsWith(gitDirPath)) {
				result.add(f);
			}
		}
		if (result.isEmpty()) {
			return null;
		}
		return result;
	}

		removeIndexDiffCaches(gitDirs);
	}

	private void removeIndexDiffCaches(List<File> gitDirs) {
		if (!gitDirs.isEmpty()) {
			IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
			if (cache != null) {
				for (File f : gitDirs) {
					cache.remove(f);
				}

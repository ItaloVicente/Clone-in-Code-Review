	private void findGitDirsRecursive(File root, Set<File> gitDirs,
			IProgressMonitor monitor, int depth) {

		if (!root.exists() || !root.isDirectory()) {
			return;
		}

		File resolved = FileKey.resolve(root, FS.DETECTED);
		if ((resolved != null) && !suppressed(root, resolved)) {
			gitDirs.add(resolved.getAbsoluteFile());
			monitor.setTaskName(NLS.bind(
					UIText.RepositorySearchDialog_RepositoriesFound_message,
					Integer.valueOf(gitDirs.size())));
		}

		if ((depth != 0) && !(resolved != null && isSameFile(root, resolved))) {
			File[] children = root.listFiles();
			if (children == null) {
				return;
			}
			for (File child : children) {
				if (monitor.isCanceled()) {
					return;
				}
				if (child.isDirectory()
						&& !Constants.DOT_GIT.equals(child.getName())) {
					monitor.subTask(child.getPath());
					findGitDirsRecursive(child, gitDirs, monitor, depth - 1);
				}
			}

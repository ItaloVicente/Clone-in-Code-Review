		File resolved = FileKey.resolve(root, FS.DETECTED);
		if (resolved != null) {
			gitDirs.add(resolved.getAbsoluteFile());
			monitor.setTaskName(NLS.bind(
					UIText.RepositorySearchDialog_RepositoriesFound_message,
					Integer.valueOf(gitDirs.size())));
		}

		if ((depth != 0) && !root.equals(resolved)) {
			File[] children = root.listFiles();
			for (File child : children) {
				if (monitor.isCanceled()) {
					return;
				}
				if (child.isDirectory()
						&& !Constants.DOT_GIT.equals(child.getName())) {
					monitor.subTask(child.getPath());
					findGitDirsRecursive(child, gitDirs, monitor, depth - 1);
				}

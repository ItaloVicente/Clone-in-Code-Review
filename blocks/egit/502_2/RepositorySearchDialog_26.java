	private void findGitDirsRecursive(File root, TreeSet<String> strings,
			IProgressMonitor monitor, boolean lookForNestedRepositories) {

		if (!root.exists() || !root.isDirectory()) {
			return;
		}
		File[] children = root.listFiles();
		if (children == null)
			return;

		for (File child : children) {
			if (monitor.isCanceled()) {
				return;
			}

			if (child.isDirectory()
					&& RepositoryCache.FileKey.isGitRepository(child)) {
				try {
					strings.add(child.getCanonicalPath());
				} catch (IOException e) {
				}
				monitor
						.setTaskName(NLS
								.bind(
										UIText.RepositorySearchDialog_RepositoriesFound_message,
										new Integer(strings.size())));
				if (!lookForNestedRepositories)
					return;
			} else if (child.isDirectory()) {
				monitor.subTask(child.getPath());
				findGitDirsRecursive(child, strings, monitor,
						lookForNestedRepositories);
			}
		}

	}


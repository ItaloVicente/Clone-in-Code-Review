	/**
	 *
	 * @param root
	 * @param strings
	 * @param monitor
	 */
	public static void recurseDir(File root, TreeSet<String> strings,
			IProgressMonitor monitor) {

		if (!root.exists() || !root.isDirectory()) {
			return;
		}
		File[] children = root.listFiles();
		for (File child : children) {
			if (monitor.isCanceled()) {
				return;
			}

			if (child.exists() && child.isDirectory()
					&& RepositoryCache.FileKey.isGitRepository(child)) {
				strings.add(child.getAbsolutePath());
				return;
			}
			if (child.isDirectory()) {
				monitor.setTaskName(child.getPath());
				recurseDir(child, strings, monitor);
			}
		}

	}

	private static String getRepositoryName(Repository repository) {
		return repository.getDirectory().getParentFile().getName();
	}


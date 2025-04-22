	/**
	 * Collect the list of .project files that are under directory into files.
	 *
	 * @param files
	 * @param directory
	 * @param visistedDirs
	 *            Set of canonical paths of directories, used as recursion guard
	 * @param monitor
	 *            The monitor to report to
	 * @return boolean <code>true</code> if the operation was completed.
	 */
	private boolean collectProjectFilesFromDirectory(Collection<File> files,
			File directory, Set<String> visistedDirs, IProgressMonitor monitor) {
		return ProjectUtil.findProjectFiles(files, directory, visistedDirs,
				monitor);
	}


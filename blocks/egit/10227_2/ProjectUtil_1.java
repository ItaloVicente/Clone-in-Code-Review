		return findProjectFiles(files, directory, searchNested, null, monitor);
	}

	private static boolean findProjectFiles(final Collection<File> files,
			final File directory, final boolean searchNested,
			final Set<String> visistedDirs, final IProgressMonitor monitor) {

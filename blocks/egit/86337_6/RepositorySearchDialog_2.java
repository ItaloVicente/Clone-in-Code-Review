	private void findGitDirsRecursive(Path root, final Set<Path> gitDirs,
			IProgressMonitor monitor, final boolean lookForNested,
			boolean skipHidden) {
		try {
			final SubMonitor m = SubMonitor.convert(monitor);
			SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {

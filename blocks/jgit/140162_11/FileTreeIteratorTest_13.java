	private final FileTreeIterator.FileModeStrategy NO_GITLINKS_STRATEGY = (
			File f
		if (attributes.isSymbolicLink()) {
			return FileMode.SYMLINK;
		} else if (attributes.isDirectory()) {
			return FileMode.TREE;
		} else if (attributes.isExecutable()) {
			return FileMode.EXECUTABLE_FILE;
		} else {
			return FileMode.REGULAR_FILE;
		}
	};

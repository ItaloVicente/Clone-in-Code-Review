	static public class NoGitlinksStrategy implements FileModeStrategy {

		public final static NoGitlinksStrategy INSTANCE = new NoGitlinksStrategy();

		@Override
		public FileMode getMode(File f
			if (attributes.isSymbolicLink()) {
				return FileMode.SYMLINK;
			} else if (attributes.isDirectory()) {
				return FileMode.TREE;
			} else if (attributes.isExecutable()) {
				return FileMode.EXECUTABLE_FILE;
			} else {
				return FileMode.REGULAR_FILE;
			}
		}
	}


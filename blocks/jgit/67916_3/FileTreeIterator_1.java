	public interface FileModeStrategy {
		FileMode getMode(File f
	}

	static public class DefaultFileModeStrategy implements FileModeStrategy {
		public final static DefaultFileModeStrategy INSTANCE =
				new DefaultFileModeStrategy();

		@Override
		public FileMode getMode(File f
			if (attributes.isSymbolicLink()) {
				return FileMode.SYMLINK;
			} else if (attributes.isDirectory()) {
				if (new File(f
					return FileMode.GITLINK;
				} else {
					return FileMode.TREE;
				}
			} else if (attributes.isExecutable()) {
				return FileMode.EXECUTABLE_FILE;
			} else {
				return FileMode.REGULAR_FILE;
			}
		}
	}



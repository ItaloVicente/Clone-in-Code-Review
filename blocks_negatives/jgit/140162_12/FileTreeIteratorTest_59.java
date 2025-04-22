	private final FileTreeIterator.FileModeStrategy NO_GITLINKS_STRATEGY =
			new FileTreeIterator.FileModeStrategy() {
				@Override
				public FileMode getMode(File f, FS.Attributes attributes) {
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
			};

	public int getType(File parent
			NoSuchFileException
		if (type == TYPE_UNKNOWN)
			type = determineType(getFileInfo(parent
		return type;
	}

	private static int determineType(int mode) {
		switch (mode & FileMode.TYPE_MASK) {
		case FileMode.TYPE_TREE:
			return TYPE_DIRECTORY;
		case FileMode.TYPE_FILE:
			return TYPE_FILE;
		case FileMode.TYPE_SYMLINK:
			return TYPE_SYMLINK;
		default:
			return TYPE_SPECIAL;
		}
	}

	public FileInfo getFileInfo(File parent
			throws AccessDeniedException
			NotDirectoryException {
		if (info == null)
			info = fs.stat(new File(parent
		return info;
	}


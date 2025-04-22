	private static PathType convert(final FileMode fileMode) {
		if (fileMode.equals(FileMode.TYPE_TREE)) {
			return PathType.DIRECTORY;
		} else if (fileMode.equals(TYPE_FILE)) {
			return PathType.FILE;
		}
		return null;
	}

	public abstract FileInfo lstat(File file) throws AccessDeniedException
			NoSuchFileException

	public abstract String readlink(File file)
			throws UnsupportedOperationException
			NoSuchFileException

	public abstract void symlink(File file
			throws UnsupportedOperationException
			NoSuchFileException

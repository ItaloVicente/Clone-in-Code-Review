
	static class Java7PosixAttributes extends Attributes {

		private Path path;

		public Java7PosixAttributes(FS fs
				boolean isDirectory
				boolean isExecutable
				boolean isSymbolicLink
				long creationTime
			super(fs
					isRegularFile
					creationTime
			this.path = pPath;
		}

		@Override
		public long getLength() {
			if (length == -1) {
				try {
					length = Files.size(path);
				} catch (IOException e) {
					length = 0;
				}
			}
			return length;
		}
	}

	@Override
	public Attributes getAttributes(File path) {
		try {
			Path nioPath = path.toPath();
			PosixFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							PosixFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Java7PosixAttributes(this
					nioPath
							.permissions().contains(
									PosixFilePermission.OWNER_EXECUTE)
					readAttributes.isSymbolicLink()
					readAttributes.isRegularFile()
							.creationTime().toMillis()
							.lastModifiedTime().toMillis());
			return attributes;
		} catch (IOException e) {
			return new Attributes(path
		}
	}

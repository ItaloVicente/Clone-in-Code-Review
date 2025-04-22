
	public static class Attributes {

		public boolean isDirectory() {
			return isDirectory;
		}

		public boolean isExecutable() {
			return isExecutable;
		}

		public boolean isSymbolicLink() {
			return isSymbolicLink;
		}

		public boolean isRegularFile() {
			return isRegularFile;
		}

		public long getCreationTime() {
			return creationTime;
		}

		public long getLastModifiedTime() {
			return lastModifiedTime;
		}

		private boolean isDirectory;

		private boolean isSymbolicLink;

		private boolean isRegularFile;

		private long creationTime;

		private long lastModifiedTime;

		private boolean isExecutable;

		private File file;

		private boolean exists;

		protected long length = -1;

		FS fs;

		Attributes(FS fs
				boolean isExecutable
				boolean isSymbolicLink
				long creationTime
			this.fs = fs;
			this.file = file;
			this.exists = exists;
			this.isDirectory = isDirectory;
			this.isExecutable = isExecutable;
			this.isSymbolicLink = isSymbolicLink;
			this.isRegularFile = isRegularFile;
			this.creationTime = creationTime;
			this.lastModifiedTime = lastModifiedTime;
		}

		public Attributes(File path
			this.file = path;
			this.fs = fs;
		}

		public long getLength() {
			if (length == -1)
				return length = file.length();
			return length;
		}

		public String getName() {
			return file.getName();
		}

		public File getFile() {
			return file;
		}

		boolean exists() {
			return exists;
		}
	}

	public Attributes getAttributes(File path) {
		boolean isDirectory = isDirectory(path);
		boolean isFile = !isDirectory && path.isFile();
		assert path.exists() == isDirectory || isFile;
		boolean exists = isDirectory || isFile;
		boolean canExecute = exists && !isDirectory && canExecute(path);
		boolean isSymlink = false;
		long lastModified = exists ? path.lastModified() : 0L;
		long createTime = 0L;
		return new Attributes(this
				isSymlink
	}

	static class Java7PosixAttributes extends Attributes {

		private Path path;

		Java7PosixAttributes(FS fs
				Path pPath
				boolean exists
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


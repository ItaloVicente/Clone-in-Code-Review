	static class Java7BasicAttributes extends Attributes {

		Java7BasicAttributes(FS fs, File fPath, boolean exists,
				boolean isDirectory, boolean isExecutable,
				boolean isSymbolicLink, boolean isRegularFile,
				long creationTime, long lastModifiedTime, long length) {
			super(fs, fPath, exists, isDirectory, isExecutable, isSymbolicLink,
					isRegularFile, creationTime, lastModifiedTime, length);
		}
	}


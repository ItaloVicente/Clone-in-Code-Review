		return new FileSnapshot(path);
	}

	private static Object getFileKey(BasicFileAttributes fileAttributes) {
		Object fileKey = fileAttributes.fileKey();
		return fileKey == null ? MISSING_FILEKEY : fileKey;

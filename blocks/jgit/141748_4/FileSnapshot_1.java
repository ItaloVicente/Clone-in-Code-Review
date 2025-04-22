		return new FileSnapshot(read
				fileKey);
	}

	private static Object getFileKey(BasicFileAttributes fileAttributes) {
		Object fileKey = fileAttributes.fileKey();
		return fileKey == null ? MISSING_FILEKEY : fileKey;

		long read = System.currentTimeMillis();
		long modified;
		long size;
		Object fileKey = null;
		Duration fsTimerResolution = FS
				.getFsTimerResolution(path.toPath().getParent());
		try {
			BasicFileAttributes fileAttributes = FS.DETECTED.fileAttributes(path);
			modified = fileAttributes.lastModifiedTime().toMillis();
			size = fileAttributes.size();
			fileKey = getFileKey(fileAttributes);
		} catch (IOException e) {
			modified = path.lastModified();
			size = path.length();
			fileKey = MISSING_FILEKEY;
		}
		return new FileSnapshot(read, modified, size, fsTimerResolution,
				fileKey);

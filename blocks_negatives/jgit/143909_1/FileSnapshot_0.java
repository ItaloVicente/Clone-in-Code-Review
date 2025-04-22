		long read = System.currentTimeMillis();
		long modified;
		long size;
		try {
			BasicFileAttributes fileAttributes = FS.DETECTED.fileAttributes(path);
			modified = fileAttributes.lastModifiedTime().toMillis();
			size = fileAttributes.size();
		} catch (IOException e) {
			modified = path.lastModified();
			size = path.length();
		}
		return new FileSnapshot(read, modified, size);

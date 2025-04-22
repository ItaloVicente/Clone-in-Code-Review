		long read = System.currentTimeMillis();
		long modified;
		try {
			modified = FS.DETECTED.lastModified(path);
		} catch (IOException e) {
			modified = path.lastModified();
		}

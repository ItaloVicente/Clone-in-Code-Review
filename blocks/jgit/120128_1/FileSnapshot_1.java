		long currLastModified;
		try {
			currLastModified = FS.DETECTED.lastModified(path);
		} catch (IOException e) {
			currLastModified = path.lastModified();
		}
		return isModified(currLastModified);

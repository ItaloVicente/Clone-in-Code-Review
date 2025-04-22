		FileStore store = null;
		try {
			store = Files.getFileStore(lockPath);
		} catch (SecurityException e) {
			return true;
		}

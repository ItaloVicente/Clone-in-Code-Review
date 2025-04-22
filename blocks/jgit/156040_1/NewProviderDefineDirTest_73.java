	@Override
	public Map<String
		try {
			tempDir = createTempDirectory();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Map<String
		gitPrefs.put(GIT_NIO_DIR
		if (!REPOSITORIES_CONTAINER_DIR.equals(dirPathName)) {
			gitPrefs.put(GIT_NIO_DIR_NAME
		}
		return gitPrefs;
	}

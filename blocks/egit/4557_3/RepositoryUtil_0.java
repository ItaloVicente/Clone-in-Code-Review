	private String getPath(File repositoryDir) {
		try {
			return repositoryDir.getCanonicalPath();
		} catch (IOException e) {
			return repositoryDir.getAbsolutePath();
		}
	}


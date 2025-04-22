
	private String resolveRelativeModulePath(String url) {
		File f = new File(url);
		if (!f.isAbsolute())
			if (repo.isBare()) {
				url = new File(repo.getDirectory()
			} else {
				url = new File(repo.getWorkTree()
			}
		return url;
	}

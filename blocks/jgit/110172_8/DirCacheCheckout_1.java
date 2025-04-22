	private void checkoutGitlink(String path
			throws IOException {
		File gitlinkDir = new File(repo.getWorkTree()
		FileUtils.mkdirs(gitlinkDir
		FS fs = repo.getFS();
		entry.setLastModified(fs.lastModified(gitlinkDir));
	}


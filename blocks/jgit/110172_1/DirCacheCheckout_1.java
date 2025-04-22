	private void checkoutGitlink(String path
		File gitlinkDir = new File(repo.getWorkTree()
		FileUtils.mkdirs(gitlinkDir
		FS fs = repo.getFS();
		entry.setLastModified(fs.lastModified(gitlinkDir));
	}


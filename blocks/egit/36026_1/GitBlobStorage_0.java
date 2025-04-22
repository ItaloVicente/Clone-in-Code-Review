
	public IPath getAbsolutePath() {
		final File repoDir;
		if (db.isBare())
			repoDir = db.getDirectory();
		else
			repoDir = db.getWorkTree();
		return new Path(repoDir.getAbsolutePath() + File.separatorChar + path);
	}

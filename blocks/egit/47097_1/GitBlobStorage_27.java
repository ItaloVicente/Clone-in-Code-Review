	public IPath getAbsolutePath() {
		if (db.isBare()) {
			return null;
		}
		return new Path(db.getWorkTree().getAbsolutePath() + File.separatorChar
				+ path);
	}

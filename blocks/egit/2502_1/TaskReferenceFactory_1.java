	private IPath getRepoRoot(Repository r) {
		if (r.isBare())
			return null;
		return new Path(r.getDirectory().getParent());
	}


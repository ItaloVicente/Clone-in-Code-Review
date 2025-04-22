	RefDatabase(final Repository r) {
		db = r;
		gitDir = db.getDirectory();
		refsDir = FS.resolve(gitDir, "refs");
		packedRefsFile = FS.resolve(gitDir, Constants.PACKED_REFS);
		clearCache();
	}

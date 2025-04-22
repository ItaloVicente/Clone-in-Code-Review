	public ReflogWriter(Repository repository, boolean forceWrite) {
		FS fs = repository.getFS();
		parent = repository;
		File gitDir = repository.getDirectory();
		logsDir = fs.resolve(gitDir, LOGS);
		logsRefsDir = fs.resolve(gitDir, LOGS + '/' + R_REFS);

		gitDir = repo.getDirectory();
		refsDir = fs.resolve(gitDir, REFS);
		logsDir = fs.resolve(gitDir, LOGS);
		logsRefsDir = fs.resolve(gitDir, LOGS_REFS);
		packedRefsFile = fs.resolve(gitDir, PACKED_REFS);


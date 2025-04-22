		gitDir = db.getDirectory();
		refsDir = fs.resolve(gitDir, R_REFS);
		logsDir = fs.resolve(gitDir, LOGS);
		logsRefsDir = fs.resolve(gitDir, LOGS + '/' + R_REFS);
		packedRefsFile = fs.resolve(gitDir, PACKED_REFS);


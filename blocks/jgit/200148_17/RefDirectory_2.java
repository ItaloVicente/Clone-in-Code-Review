	RefDirectory(RefDirectory refDb) {
		parent = refDb.parent;
		gitDir = refDb.gitDir;
		refsDir = refDb.refsDir;
		logsDir = refDb.logsDir;
		logsRefsDir = refDb.logsRefsDir;
		packedRefsFile = refDb.packedRefsFile;
		looseRefs.set(refDb.looseRefs.get());
		packedRefs.set(refDb.packedRefs.get());
		trustFolderStat = refDb.trustFolderStat;
		trustPackedRefsStat = refDb.trustPackedRefsStat;
	}


		setPackedGitOpenFiles(rc.getInt(
				"core", null, "packedgitopenfiles", getPackedGitOpenFiles())); //$NON-NLS-1$ //$NON-NLS-2$
		setPackedGitLimit(rc.getLong(
				"core", null, "packedgitlimit", getPackedGitLimit())); //$NON-NLS-1$ //$NON-NLS-2$
		setPackedGitWindowSize(rc.getInt(
				"core", null, "packedgitwindowsize", getPackedGitWindowSize())); //$NON-NLS-1$ //$NON-NLS-2$
		setPackedGitMMAP(rc.getBoolean(
				"core", null, "packedgitmmap", isPackedGitMMAP())); //$NON-NLS-1$ //$NON-NLS-2$
		setDeltaBaseCacheLimit(rc.getInt(
				"core", null, "deltabasecachelimit", getDeltaBaseCacheLimit())); //$NON-NLS-1$ //$NON-NLS-2$

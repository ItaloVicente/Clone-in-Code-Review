		refBlockSize = rc.getInt("reftable", "blockSize", refBlockSize); //$NON-NLS-1$ //$NON-NLS-2$
		logBlockSize = rc.getInt("reftable", "logBlockSize", logBlockSize); //$NON-NLS-1$ //$NON-NLS-2$
		restartInterval = rc.getInt("reftable", "restartInterval", restartInterval); //$NON-NLS-1$ //$NON-NLS-2$
		maxIndexLevels = rc.getInt("reftable", "indexLevels", maxIndexLevels); //$NON-NLS-1$ //$NON-NLS-2$
		alignBlocks = rc.getBoolean("reftable", "alignBlocks", alignBlocks); //$NON-NLS-1$ //$NON-NLS-2$
		indexObjects = rc.getBoolean("reftable", "indexObjects", indexObjects); //$NON-NLS-1$ //$NON-NLS-2$

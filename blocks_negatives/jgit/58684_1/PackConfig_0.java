		setReuseObjects(rc.getBoolean("pack", "reuseobjects", isReuseObjects())); //$NON-NLS-1$ //$NON-NLS-2$
		setDeltaCompress(rc.getBoolean(
				"pack", "deltacompression", isDeltaCompress())); //$NON-NLS-1$ //$NON-NLS-2$
		setCutDeltaChains(rc.getBoolean(
				"pack", "cutdeltachains", getCutDeltaChains())); //$NON-NLS-1$ //$NON-NLS-2$
		setBuildBitmaps(rc.getBoolean("pack", "buildbitmaps", isBuildBitmaps())); //$NON-NLS-1$ //$NON-NLS-2$

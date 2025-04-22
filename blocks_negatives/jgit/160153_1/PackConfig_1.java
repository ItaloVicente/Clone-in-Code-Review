		setReuseDeltas(rc.getBoolean("pack", "reusedeltas", isReuseDeltas())); //$NON-NLS-1$ //$NON-NLS-2$
		setReuseObjects(
				rc.getBoolean("pack", "reuseobjects", isReuseObjects())); //$NON-NLS-1$ //$NON-NLS-2$
		setDeltaCompress(
				rc.getBoolean("pack", "deltacompression", isDeltaCompress())); //$NON-NLS-1$ //$NON-NLS-2$
		setCutDeltaChains(
				rc.getBoolean("pack", "cutdeltachains", getCutDeltaChains())); //$NON-NLS-1$ //$NON-NLS-2$
		setSinglePack(
				rc.getBoolean("pack", "singlepack", getSinglePack())); //$NON-NLS-1$ //$NON-NLS-2$
		setBuildBitmaps(
				rc.getBoolean("pack", "buildbitmaps", isBuildBitmaps())); //$NON-NLS-1$ //$NON-NLS-2$
		setBitmapContiguousCommitCount(
				rc.getInt("pack", "bitmapcontiguouscommitcount", //$NON-NLS-1$ //$NON-NLS-2$
						getBitmapContiguousCommitCount()));
		setBitmapRecentCommitCount(rc.getInt("pack", "bitmaprecentcommitcount", //$NON-NLS-1$ //$NON-NLS-2$

		setBitmapExcessiveBranchCount(rc.getInt("pack", //$NON-NLS-1$
				"bitmapexcessivebranchcount", getBitmapExcessiveBranchCount())); //$NON-NLS-1$
		setBitmapInactiveBranchAgeInDays(
				rc.getInt("pack", "bitmapinactivebranchageindays", //$NON-NLS-1$ //$NON-NLS-2$
						getBitmapInactiveBranchAgeInDays()));
		setWaitPreventRacyPack(rc.getBoolean("pack", "waitpreventracypack", //$NON-NLS-1$ //$NON-NLS-2$
				isWaitPreventRacyPack()));
		setMinSizePreventRacyPack(rc.getLong("pack", "minsizepreventracypack", //$NON-NLS-1$//$NON-NLS-2$

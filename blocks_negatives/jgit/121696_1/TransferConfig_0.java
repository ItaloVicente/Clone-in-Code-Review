		boolean fsck = rc.getBoolean("transfer", "fsckobjects", false); //$NON-NLS-1$ //$NON-NLS-2$
		fetchFsck = rc.getBoolean("fetch", "fsckobjects", fsck); //$NON-NLS-1$ //$NON-NLS-2$
		receiveFsck = rc.getBoolean("receive", "fsckobjects", fsck); //$NON-NLS-1$ //$NON-NLS-2$
		fsckSkipList = rc.getString(FSCK, null, "skipList"); //$NON-NLS-1$
		allowInvalidPersonIdent = rc.getBoolean(FSCK, "allowInvalidPersonIdent", false); //$NON-NLS-1$
		safeForWindows = rc.getBoolean(FSCK, "safeForWindows", //$NON-NLS-1$

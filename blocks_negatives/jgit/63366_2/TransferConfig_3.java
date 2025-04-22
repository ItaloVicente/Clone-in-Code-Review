		checkReceivedObjects = rc.getBoolean(
				"fetch", "fsckobjects", //$NON-NLS-1$ //$NON-NLS-2$
				rc.getBoolean("transfer", "fsckobjects", false)); //$NON-NLS-1$ //$NON-NLS-2$
		allowLeadingZeroFileMode = checkReceivedObjects
				&& rc.getBoolean("fsck", "allowLeadingZeroFileMode", false); //$NON-NLS-1$ //$NON-NLS-2$
		allowInvalidPersonIdent = checkReceivedObjects
				&& rc.getBoolean("fsck", "allowInvalidPersonIdent", false); //$NON-NLS-1$ //$NON-NLS-2$
		safeForWindows = checkReceivedObjects
				&& rc.getBoolean("fsck", "safeForWindows", //$NON-NLS-1$ //$NON-NLS-2$

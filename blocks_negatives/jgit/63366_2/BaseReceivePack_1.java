			checkReceivedObjects = config.getBoolean(
					"receive", "fsckobjects", //$NON-NLS-1$ //$NON-NLS-2$
					config.getBoolean("transfer", "fsckobjects", false)); //$NON-NLS-1$ //$NON-NLS-2$
			allowLeadingZeroFileMode = checkReceivedObjects
					&& config.getBoolean("fsck", "allowLeadingZeroFileMode", false); //$NON-NLS-1$ //$NON-NLS-2$
			allowInvalidPersonIdent = checkReceivedObjects
					&& config.getBoolean("fsck", "allowInvalidPersonIdent", false); //$NON-NLS-1$ //$NON-NLS-2$
			safeForWindows = checkReceivedObjects
					&& config.getBoolean("fsck", "safeForWindows", false); //$NON-NLS-1$ //$NON-NLS-2$
			safeForMacOS = checkReceivedObjects
					&& config.getBoolean("fsck", "safeForMacOS", false); //$NON-NLS-1$ //$NON-NLS-2$


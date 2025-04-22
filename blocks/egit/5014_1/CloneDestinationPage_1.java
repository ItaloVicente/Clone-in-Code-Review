	void saveSettingsForClonedRepo() {
		clonedDestination = getDestinationFile();
		clonedInitialBranch = getInitialBranch();
		clonedRemote = getRemote();
	}

	boolean cloneSettingsChanged() {
		boolean cloneSettingsChanged = false;
		if (clonedDestination == null || !clonedDestination.equals(getDestinationFile()) ||
				clonedInitialBranch == null || !clonedInitialBranch.equals(getInitialBranch()) ||
				clonedRemote == null || !clonedRemote.equals(getRemote()))
			cloneSettingsChanged = true;
		return cloneSettingsChanged;
	}


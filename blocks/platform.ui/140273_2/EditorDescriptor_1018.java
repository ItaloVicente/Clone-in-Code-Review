		return getOpenMode() == OPEN_EXTERNAL;
	}

	protected boolean loadValues(IMemento memento) {
		editorName = memento.getString(IWorkbenchConstants.TAG_LABEL);
		imageFilename = memento.getString(IWorkbenchConstants.TAG_IMAGE);
		className = memento.getString(IWorkbenchConstants.TAG_CLASS);
		launcherName = memento.getString(IWorkbenchConstants.TAG_LAUNCHER);
		fileName = memento.getString(IWorkbenchConstants.TAG_FILE);
		id = Util.safeString(memento.getString(IWorkbenchConstants.TAG_ID));
		pluginIdentifier = memento.getString(IWorkbenchConstants.TAG_PLUGIN);

		Integer openModeInt = memento.getInteger(IWorkbenchConstants.TAG_OPEN_MODE);
		if (openModeInt != null) {
			openMode = openModeInt.intValue();
		} else {
			boolean internal = Boolean.parseBoolean(memento.getString(IWorkbenchConstants.TAG_INTERNAL));
			boolean openInPlace = Boolean.parseBoolean(memento.getString(IWorkbenchConstants.TAG_OPEN_IN_PLACE));
			if (internal) {
				openMode = OPEN_INTERNAL;
			} else {
				if (openInPlace) {
					openMode = OPEN_INPLACE;
				} else {
					openMode = OPEN_EXTERNAL;
				}
			}

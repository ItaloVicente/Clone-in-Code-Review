        return getOpenMode() == OPEN_EXTERNAL;
    }

    /**
     * Load the object properties from a memento.
     *
     * @return <code>true</code> if the values are valid, <code>false</code> otherwise
     */
    protected boolean loadValues(IMemento memento) {
        editorName = memento.getString(IWorkbenchConstants.TAG_LABEL);
        imageFilename = memento.getString(IWorkbenchConstants.TAG_IMAGE);
        className = memento.getString(IWorkbenchConstants.TAG_CLASS);
        launcherName = memento.getString(IWorkbenchConstants.TAG_LAUNCHER);
        fileName = memento.getString(IWorkbenchConstants.TAG_FILE);
        id = Util.safeString(memento.getString(IWorkbenchConstants.TAG_ID));
        pluginIdentifier = memento.getString(IWorkbenchConstants.TAG_PLUGIN);

        Integer openModeInt = memento
                .getInteger(IWorkbenchConstants.TAG_OPEN_MODE);
        if (openModeInt != null) {
            openMode = openModeInt.intValue();
        } else {
            boolean internal = Boolean.parseBoolean(memento
                    .getString(IWorkbenchConstants.TAG_INTERNAL));
            boolean openInPlace = Boolean.parseBoolean(memento
                    .getString(IWorkbenchConstants.TAG_OPEN_IN_PLACE));
            if (internal) {
                openMode = OPEN_INTERNAL;
            } else {
                if (openInPlace) {
                    openMode = OPEN_INPLACE;
                } else {
                    openMode = OPEN_EXTERNAL;
                }
            }
        }
        if (openMode != OPEN_EXTERNAL && openMode != OPEN_INTERNAL
                && openMode != OPEN_INPLACE) {
            WorkbenchPlugin
            return false;
        }

        String programName = memento
                .getString(IWorkbenchConstants.TAG_PROGRAM_NAME);
        if (programName != null) {
            this.program = findProgram(programName);
        }
        return true;
    }

    /**
     * Save the object values in a IMemento
     */
    protected void saveValues(IMemento memento) {
        memento.putString(IWorkbenchConstants.TAG_LABEL, getLabel());
        memento.putString(IWorkbenchConstants.TAG_IMAGE, getImageFilename());
        memento.putString(IWorkbenchConstants.TAG_CLASS, getClassName());
        memento.putString(IWorkbenchConstants.TAG_LAUNCHER, getLauncher());
        memento.putString(IWorkbenchConstants.TAG_FILE, getFileName());
        memento.putString(IWorkbenchConstants.TAG_ID, getId());
        memento.putString(IWorkbenchConstants.TAG_PLUGIN, getPluginId());

        memento.putInteger(IWorkbenchConstants.TAG_OPEN_MODE, getOpenMode());
        memento.putString(IWorkbenchConstants.TAG_INTERNAL, String
                .valueOf(isInternal()));
        memento.putString(IWorkbenchConstants.TAG_OPEN_IN_PLACE, String
                .valueOf(isOpenInPlace()));

        if (this.program != null) {
			memento.putString(IWorkbenchConstants.TAG_PROGRAM_NAME,
                    this.program.getName());

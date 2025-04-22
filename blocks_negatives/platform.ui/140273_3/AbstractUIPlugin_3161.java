        Display.getDefault().asyncExec(() -> WWinPluginAction.refreshActionList());
    }

    /**
     * Saves this plug-in's dialog settings.
     * Any problems which arise are silently ignored.
     */
    protected void saveDialogSettings() {
        if (dialogSettings == null) {
            return;
        }

        try {
        	IPath path = getStateLocationOrNull();
        	if(path == null) {

    /**
     * Gets the text of the clean dialog, depending on whether the
     * workspace is currently in autobuild mode.
     * @return String the question the user will be asked.
     */
    private static String getQuestion() {
        boolean autoBuilding = ResourcesPlugin.getWorkspace().isAutoBuilding();
        if (autoBuilding) {
            return IDEWorkbenchMessages.CleanDialog_buildCleanAuto;
        }
        return IDEWorkbenchMessages.CleanDialog_buildCleanManual;
    }

    /**
     * Creates a new clean dialog.
     *
     * @param window the window to create it in
     * @param selection the currently selected projects (may be empty)
     */
    public CleanDialog(IWorkbenchWindow window, IProject[] selection) {

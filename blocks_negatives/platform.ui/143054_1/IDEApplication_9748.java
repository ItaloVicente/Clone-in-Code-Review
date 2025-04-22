            String propertyName, Object data) {
    }

    /**
     * Return <code>null</code> if a valid workspace path has been set and an exit code otherwise.
     * Prompt for and set the path if possible and required.
     *
     * @param applicationArguments the command line arguments
     * @return <code>null</code> if a valid instance location has been set and an exit code
     *         otherwise
     */
    @SuppressWarnings("rawtypes")
	private Object checkInstanceLocation(Shell shell, Map applicationArguments) {
        Location instanceLoc = Platform.getInstanceLocation();
        if (instanceLoc == null) {
            MessageDialog
                    .openError(
                            shell,
                            IDEWorkbenchMessages.IDEApplication_workspaceMandatoryTitle,
                            IDEWorkbenchMessages.IDEApplication_workspaceMandatoryMessage);
            return EXIT_OK;
        }

        if (instanceLoc.isSet()) {
            if (!checkValidWorkspace(shell, instanceLoc.getURL())) {

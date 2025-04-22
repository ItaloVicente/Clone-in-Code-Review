			String propertyName, Object data) {
	}

	@SuppressWarnings("rawtypes")
	protected Object checkInstanceLocation(Shell shell, Map applicationArguments) {
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

			readHandler(extPoint, configElement);
		}
	}

	private void readHandler(IExtensionPoint extPoint, IConfigurationElement configElement) {

		String commandId = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_COMMAND_ID);
		if (commandId == null || commandId.length() == 0) {
			return;
		}
		String defaultHandler = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS);
		if ((defaultHandler == null)
				&& (configElement.getChildren(IWorkbenchRegistryConstants.TAG_CLASS).length == 0)) {
			return;
		}
		Expression activeWhen = null;
		final IConfigurationElement[] awChildren = configElement
				.getChildren(IWorkbenchRegistryConstants.TAG_ACTIVE_WHEN);
		if (awChildren.length > 0) {
			final IConfigurationElement[] subChildren = awChildren[0].getChildren();
			if (subChildren.length != 1) {
				Activator.trace(Policy.DEBUG_CMDS,
						"Incorrect activeWhen element " + commandId, null); //$NON-NLS-1$
				return;

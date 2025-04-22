		IConfigurationElement parameters[] = configElement
				.getChildren(IWorkbenchRegistryConstants.TAG_PARAMETER);

		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i]
					.getAttribute(IWorkbenchRegistryConstants.ATT_NAME).equals(
							PREFIX)) {
				prefix = parameters[i]
						.getAttribute(IWorkbenchRegistryConstants.ATT_VALUE);

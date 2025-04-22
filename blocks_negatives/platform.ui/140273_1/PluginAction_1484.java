        if (configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ENABLES_FOR) != null) {
            enabler = new SelectionEnabler(configElement);
        } else {
			IConfigurationElement[] kids = configElement
					.getChildren(IWorkbenchRegistryConstants.TAG_ENABLEMENT);
			IConfigurationElement[] kids2 = configElement
					.getChildren(IWorkbenchRegistryConstants.TAG_SELECTION);
			if (kids.length > 0 || kids2.length>0) {

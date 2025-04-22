		String initialEnabledAttr = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_INITIAL_ENABLED);
		if (initialEnabledAttr != null) {
			boolean initialEnabled = Boolean.parseBoolean(initialEnabledAttr);
			setEnabled(initialEnabled);
		}


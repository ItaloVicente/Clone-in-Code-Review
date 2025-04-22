		return true;
	}

	private boolean processExtension(IConfigurationElement element) {
		IConfigurationElement[] children = element.getChildren();
		for (IConfigurationElement child : children) {
			String type = child.getName();
			if (includeTag(type)) {
				boolean result = false;
				if (type.equals(IWorkbenchRegistryConstants.TAG_ACTION_SET)) {

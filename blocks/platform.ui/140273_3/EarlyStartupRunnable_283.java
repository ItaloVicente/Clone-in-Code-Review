		for (IConfigurationElement element : configElements) {
			if (element != null && element.getName().equals(IWorkbenchConstants.TAG_STARTUP)) {
				runEarlyStartup(WorkbenchPlugin.createExtension(element, IWorkbenchConstants.TAG_CLASS));
			}
		}
	}

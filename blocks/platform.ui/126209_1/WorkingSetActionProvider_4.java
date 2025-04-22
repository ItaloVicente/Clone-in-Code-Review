		setWorkingSet(newWorkingSet);
		if (newWorkingSet != null) {
			if (!contentService.isActive(WorkingSetsContentProvider.EXTENSION_ID)) {
				contentService.getActivationService()
						.activateExtensions(new String[] { WorkingSetsContentProvider.EXTENSION_ID }, false);
				contentService.getActivationService().persistExtensionActivations();

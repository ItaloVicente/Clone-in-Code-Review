		try {
			resourcesInScope = findRelatedChanges(part, resources);
		} catch (InvocationTargetException e) {
			Activator.handleError(
					UIText.CommitActionHandler_errorBuildingScope,
					e.getCause(), true);

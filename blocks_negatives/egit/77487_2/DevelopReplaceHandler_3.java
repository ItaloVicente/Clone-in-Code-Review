
		IResource[] resourcesInScope;
		try {
			resourcesInScope = getRelatedChanges(part, selectedResources);
		} catch (InterruptedException e) {
			return null;
		}

		return new DiscardChangesOperation(resourcesInScope, revision);

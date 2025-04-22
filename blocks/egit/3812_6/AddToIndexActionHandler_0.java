
		IResource[] resourcesInScope;
		try {
			IWorkbenchPart part = getPart(event);
			resourcesInScope = GitScopeUtil.getRelatedChanges(part, sel);
		} catch (InterruptedException e) {
			return null;
		}

		final AddToIndexOperation operation = new AddToIndexOperation(
				resourcesInScope);

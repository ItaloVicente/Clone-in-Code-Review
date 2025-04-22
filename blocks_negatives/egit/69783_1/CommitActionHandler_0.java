		final Repository repo = getRepository(true, event);
		if (repo == null)
			return null;
		final Shell shell = getShell(event);
		IResource[] resourcesInScope;
		try {
			IResource[] selectedResources = getSelectedResources(event);
			if (selectedResources.length > 0) {
				IWorkbenchPart part = getPart(event);
				resourcesInScope = GitScopeUtil.getRelatedChanges(part,
						selectedResources);
			} else
				resourcesInScope = new IResource[0];
		} catch (InterruptedException e) {

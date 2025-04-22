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
				return null;

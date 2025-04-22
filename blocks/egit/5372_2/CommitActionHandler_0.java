			if (selectedResources.length > 0) {
				IWorkbenchPart part = getPart(event);
				resourcesInScope = GitScopeUtil.getRelatedChanges(part,
						selectedResources);
			} else
				resourcesInScope = new IResource[0];

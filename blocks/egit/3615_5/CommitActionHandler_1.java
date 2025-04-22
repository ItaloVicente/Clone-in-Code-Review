		final Repository[] repos = getRepositoriesFor(getProjectsForSelectedResources(event));
		final Shell shell = getShell(event);
		IResource[] resourcesInScope;
		try {
			IResource[] selectedResources = getSelectedResources(event);
			IWorkbenchPart part = getPart(event);
			resourcesInScope = GitScopeUtil.getRelatedChanges(part,
					selectedResources);
		} catch (InterruptedException e) {
			return null;
		}
		CommitUI commitUi = new CommitUI(shell, repos[0], resourcesInScope,
				false);
		commitUi.commit();

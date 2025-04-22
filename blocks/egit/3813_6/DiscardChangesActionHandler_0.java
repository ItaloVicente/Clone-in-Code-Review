
	private DiscardChangesOperation createOperation(IWorkbenchPart part, ExecutionEvent event)
			throws ExecutionException {

		IResource[] selectedResources = gatherResourceToOperateOn(event);
		String revision;
		try {
			revision = gatherRevision(event);
		} catch (OperationCanceledException e) {
			return null;
		}

		IResource[] resourcesInScope;
		try {
			resourcesInScope = GitScopeUtil.getRelatedChanges(part, selectedResources);
		} catch (InterruptedException e) {
			return null;
		}

		return new DiscardChangesOperation(resourcesInScope, revision);

	}


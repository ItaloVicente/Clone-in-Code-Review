
		DeletePathsOperation operation = new DeletePathsOperation(paths);

		try {
			operation.execute(null);
		} catch (CoreException e) {
			Activator.handleError(UIText.DeleteResourcesOperationUI_deleteFailed, e, true);

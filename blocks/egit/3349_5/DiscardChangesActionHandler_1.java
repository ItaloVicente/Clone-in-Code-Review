	protected DiscardChangesOperation createOperation(ExecutionEvent event)
			throws ExecutionException {
		return new DiscardChangesOperation(getSelectedResources(event), null);

	}


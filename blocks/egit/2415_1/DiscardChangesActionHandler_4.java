
		final DiscardChangesOperation operation;
		switch (replace) {
		case INDEX:
			operation = new ReplaceFromIndexOperation(getSelectedResources(event));
			break;
		case HEAD:
			operation = new ReplaceFromHEADOperation(getSelectedResources(event));
			break;
		default:
			return null;
		}


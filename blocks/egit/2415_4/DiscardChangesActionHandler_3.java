
		final DiscardChangesOperation operation;
		switch (replace) {
		case INDEX:
			operation = new ReplaceWithIndexOperation(getSelectedResources(event));
			break;
		case HEAD:
			operation = new ReplaceWithHeadOperation(getSelectedResources(event));
			break;
		default:
			return null;
		}


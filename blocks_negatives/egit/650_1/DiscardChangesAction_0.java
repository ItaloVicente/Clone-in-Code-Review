		if (performAction) {
			performDiscardChanges();
		}
	}

	private void performDiscardChanges() {
		ArrayList<IResource> allFiles = new ArrayList<IResource>();

		for (IResource res : getSelectedResources()) {
			allFiles.addAll(getAllMembers(res));
		}

		for (IResource res : allFiles) {
			try {
				discardChange(res);
			} catch (IOException e1) {
				Activator.handleError(UIText.DiscardChangesAction_unexpectedErrorMessage, e1, true);
			}catch (RuntimeException e2) {
				Activator.handleError(UIText.DiscardChangesAction_unexpectedIndexErrorMessage, e2, true);

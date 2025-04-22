	private void synchronizeModel(final IFile file, Repository repo,
			String refName) {
		try {
			GitModelSynchronize.synchronizeModelWithWorkspace(file, repo,
					refName);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
			return;
		}

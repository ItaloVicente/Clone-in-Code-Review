	@Override
	public boolean isConnected() {
		return sharedDocumentAdapter != null
				&& sharedDocumentAdapter.isConnected();
	}

	@Override
	public boolean saveDocument(boolean overwrite, IProgressMonitor monitor)
			throws CoreException {
		if (isConnected()) {
			IEditorInput input = sharedDocumentAdapter.getDocumentKey(this);
			sharedDocumentAdapter.saveDocument(input, overwrite, monitor);
			updateGitState();
			refreshTimestamp();
			return true;
		}
		return false;
	}


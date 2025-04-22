	}

	public void setSelection(ISelection selection) {
		currentSelection = selection;
		for (ISelectionChangedListener listener : selectionListeners) {
			listener.selectionChanged(new SelectionChangedEvent(
					RepositoriesView.this, selection));
		}
	}

	/**
	 * Opens the tree and marks the folder to which a project is pointing
	 *
	 * @param resource
	 *            TODO exceptions?
	 */
	@SuppressWarnings("unchecked")
	public void showResource(final IResource resource) {
		IProject project = resource.getProject();
		RepositoryMapping mapping = RepositoryMapping.getMapping(project);
		if (mapping == null)
			return;

		if (addDir(mapping.getRepository().getDirectory())) {
			scheduleRefresh();
		}

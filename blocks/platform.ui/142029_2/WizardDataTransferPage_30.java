		return (new Path(text)).makeAbsolute();
	}

	protected IPath queryForContainer(IContainer initialSelection, String msg) {
		return queryForContainer(initialSelection, msg, null);
	}

	protected IPath queryForContainer(IContainer initialSelection, String msg, String title) {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getControl().getShell(), initialSelection,
				allowNewContainerName(), msg);
		if (title != null) {

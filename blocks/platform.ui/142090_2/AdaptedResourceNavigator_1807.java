	}

	public ResourceSorter getSorter() {
		return (ResourceSorter) getViewer().getSorter();
	}

	public TreeViewer getViewer() {
		return viewer;
	}

	public Shell getShell() {
		return getViewSite().getShell();
	}

	String getStatusLineMessage(IStructuredSelection selection) {
		if (selection.size() == 1) {
			Object o = selection.getFirstElement();
			if (o instanceof IResource) {
				return ((IResource) o).getFullPath().makeRelative().toString();
			}

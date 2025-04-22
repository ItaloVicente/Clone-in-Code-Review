		return getViewSite().getShell();
	}

	protected String getStatusLineMessage(IStructuredSelection selection) {
		if (selection.size() == 1) {
			Object o = selection.getFirstElement();
			if (o instanceof IResource) {
				return ((IResource) o).getFullPath().makeRelative().toString();
			}
			return ResourceNavigatorMessages.ResourceNavigator_oneItemSelected;
		}
		if (selection.size() > 1) {
			return NLS.bind(ResourceNavigatorMessages.ResourceNavigator_statusLine, String.valueOf(selection.size()));
		}
		return ""; //$NON-NLS-1$
	}

	String getFrameName(Object element) {
		if (element instanceof IResource) {

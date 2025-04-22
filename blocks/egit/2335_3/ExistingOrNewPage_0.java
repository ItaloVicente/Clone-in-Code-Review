	public Map<IProject, File> getProjects(boolean checked) {
		final Object[] elements;
		if (checked)
			elements = viewer.getCheckedElements();
		else {
			ISelection selection = viewer.getSelection();
			if (selection instanceof IStructuredSelection)
				elements = ((IStructuredSelection) selection).toArray();
			else
				elements = new Object[0];
		}
		Map<IProject, File> ret = new HashMap<IProject, File>(elements.length);
		for (Object ti : elements) {

	public ShowInContext getShowInContext() {
		if (stagedTableViewer != null && stagedTableViewer.getTable().isFocusControl())
			return getShowInContext(stagedTableViewer);
		else if (unstagedTableViewer != null && unstagedTableViewer.getTable().isFocusControl())
			return getShowInContext(unstagedTableViewer);
		else
			return null;
	}

	private ShowInContext getShowInContext(TableViewer tableViewer) {
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		List<IResource> resources = new ArrayList<IResource>();
		for (Object element : selection.toList()) {
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				IFile file = entry.getFile();
				if (file != null)
					resources.add(file);
			}
		}
		return new ShowInContext(null, new StructuredSelection(resources));
	}


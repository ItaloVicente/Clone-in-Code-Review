		Object element = selection.getFirstElement();
		if (element instanceof IFile) {
			openFileAction.selectionChanged(selection);
			openFileAction.run();
		}
	}

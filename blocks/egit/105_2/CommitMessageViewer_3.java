
	public void setDb(Repository db) {
		this.db = db;
	}

	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection)selection;
			Object obj = sel.getFirstElement();
			if (obj instanceof FileDiff) {
				String path = ((FileDiff)obj).path;
				findAndSelect(0, formatPathLine(path), true, true, false, false);
			}
		}

	}

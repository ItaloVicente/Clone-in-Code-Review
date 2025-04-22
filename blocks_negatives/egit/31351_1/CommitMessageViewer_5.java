	public void selectionChanged(SelectionChangedEvent event) {
		currentDiffs.clear();
		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			for (Object obj : sel.toList())
				if (obj instanceof FileDiff)
					currentDiffs.add((FileDiff) obj);
		}
		format();
	}


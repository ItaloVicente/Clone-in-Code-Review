	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.add(listener);
	}

	public ISelection getSelection() {
		return currentSelection;
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionListeners.remove(listener);

	}

	public void setSelection(ISelection selection) {
		currentSelection = selection;
		for (ISelectionChangedListener listener : selectionListeners) {
			listener.selectionChanged(new SelectionChangedEvent(
					RepositoriesView.this, selection));
		}
	}


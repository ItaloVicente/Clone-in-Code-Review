			ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	public void setSelection(Object obj) {
		setSelection(new StructuredSelection(obj));
	}

	@Override

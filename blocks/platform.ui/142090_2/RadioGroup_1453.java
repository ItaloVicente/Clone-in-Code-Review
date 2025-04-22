	protected boolean fireWidgetChangeSelectionEvent(SelectionEvent e) {
		for (VetoableSelectionListener listener : widgetChangeListeners) {
			listener.canWidgetChangeSelection(e);
			if (!e.doit) {
				rollbackSelection();
				return false;
			}
		}
		return true;
	}

	private void rollbackSelection() {

		return viewer.getControl();
	}

	public void handleEntrySelection(ISelection selection) {
		if (defaultsAction != null) {
			if (selection.isEmpty()) {
				defaultsAction.setEnabled(false);
				return;
			}
			boolean editable = viewer.getActiveCellEditor() != null;
			defaultsAction.setEnabled(editable);
		}
	}

	protected void initDragAndDrop() {
		int operations = DND.DROP_COPY;
		Transfer[] transferTypes = new Transfer[] { TextTransfer.getInstance() };
		DragSourceListener listener = new DragSourceAdapter() {
			@Override

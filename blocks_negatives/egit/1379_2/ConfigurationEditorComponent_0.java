	private void saveAndUpdate() {
		try {
			editableConfig.save();
			ISelection sel = tv.getSelection();
			tv.setInput(editableConfig);
			tv.expandAll();
			tv.setSelection(sel, true);
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}

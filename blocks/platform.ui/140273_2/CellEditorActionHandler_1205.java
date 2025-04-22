		if (activeEditor == editor) {
			activeEditor.removePropertyChangeListener(cellListener);
			activeEditor = null;
		}

		Control control = editor.getControl();
		if (control != null) {
			controlToEditor.remove(control);
			if (!control.isDisposed()) {
				control.removeListener(SWT.Activate, controlListener);
				control.removeListener(SWT.Deactivate, controlListener);
			}
		}
	}

	public void setCopyAction(IAction action) {
		if (copyAction == action) {

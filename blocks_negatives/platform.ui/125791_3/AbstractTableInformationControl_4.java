	/**
	 * True if the focus is still in one of the control elements.
	 *
	 * @return
	 */
	public boolean hasFocus() {
		if (fShell == null || fShell.isDisposed()) {
			return false;
		}
		Control fc = fShell.getDisplay().getFocusControl();
		return fc == fFilterText || fc == fTableViewer.getTable() || fc == fComposite || fc == fShell;
	}


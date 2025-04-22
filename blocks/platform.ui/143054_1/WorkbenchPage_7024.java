		final boolean[] ret = new boolean[1];
		BusyIndicator.showWhile(null, () -> ret[0] = close(true, true));
		return ret[0];
	}

	public boolean closeAllSavedEditors() {
		IEditorReference editors[] = getEditorReferences();
		IEditorReference savedEditors[] = new IEditorReference[editors.length];
		int j = 0;

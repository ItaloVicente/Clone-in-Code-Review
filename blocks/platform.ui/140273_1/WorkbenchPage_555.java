		return ret[0];
	}

	public boolean closeAllSavedEditors() {
		IEditorReference editors[] = getEditorReferences();
		IEditorReference savedEditors[] = new IEditorReference[editors.length];
		int j = 0;

		innerEditors[activeEditorIndex].setFocus();
	}

	public final IEditorPart getActiveEditor() {
		return innerEditors[activeEditorIndex];
	}

	public final IEditorPart[] getInnerEditors() {
		return innerEditors;
	}

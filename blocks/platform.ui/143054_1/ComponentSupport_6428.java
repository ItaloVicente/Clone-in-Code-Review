	public static IEditorPart getSystemInPlaceEditor() {
		if (inPlaceEditorSupported()) {
			return getOleEditor();
		}
		return null;
	}

	static void openQuickOutline(IDocument document,
			ISelectionProvider selectionProvider) {
		new QuickOutlinePopup(document, selectionProvider).open();
	}

	private static class QuickOutlinePopup extends PopupDialog {
		private DiffEditorOutlinePage delegate;

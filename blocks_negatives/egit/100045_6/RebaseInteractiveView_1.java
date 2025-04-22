	private void reactOnInitalSelection() {
		selectionChangedListener.selectionChanged(initialSelection.activeEditor,
				initialSelection.selection);
		this.initialSelection = null;
	}

	private static final class InitialSelection {
		ISelection selection;

		IEditorPart activeEditor;

		InitialSelection(ISelection selection) {
			this.selection = selection;
		}
	}


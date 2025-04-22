			if (activeEditor.getSite().getSelectionProvider() != null) {
				ISelection selection = activeEditor.getSite().getSelectionProvider().getSelection();
				if (originalSel.equals(selection)) {
					bootstrapSelection = originalSel;
					return activeEditor;
				}

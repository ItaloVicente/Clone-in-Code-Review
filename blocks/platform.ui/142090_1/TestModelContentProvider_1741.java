		StructuredSelection selection = new StructuredSelection(change
				.getChildren());
		if ((change.getModifiers() & TestModelChange.SELECT) != 0) {
			((StructuredViewer) fViewer).setSelection(selection);
		}
		if ((change.getModifiers() & TestModelChange.REVEAL) != 0) {
			Object element = selection.getFirstElement();
			if (element != null) {
				((StructuredViewer) fViewer).reveal(element);
			}
		}
	}

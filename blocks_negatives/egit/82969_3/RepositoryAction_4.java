		IStructuredSelection selectionBefore = handler.getSelection();
		handler.setSelection(mySelection);
		if (action != null) {
			IStructuredSelection selectionAfter = handler.getSelection();
			boolean equalSelection = (selectionBefore == null) ? selectionAfter == null
					: selectionBefore.equals(selectionAfter);
			if (!equalSelection)

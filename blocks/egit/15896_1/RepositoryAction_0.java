		if (action != null) {
			IStructuredSelection selectionAfter = handler.getSelection();
			boolean equalSelection = (selectionBefore == null) ? selectionAfter == null
					: selectionBefore.equals(selectionAfter);
			if (!equalSelection)
				action.setEnabled(isEnabled());
		}

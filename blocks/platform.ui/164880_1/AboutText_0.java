		styledText.addTraverseListener(e -> {
			switch (e.detail) {
			case SWT.TRAVERSE_ESCAPE:
				e.doit = true;
				break;
			case SWT.TRAVERSE_TAB_NEXT:
				Point nextSelection = styledText.getSelection();
				int charCount = styledText.getCharCount();
				if ((nextSelection.x == charCount) && (nextSelection.y == charCount)) {
					styledText.setSelection(0);
				}
				StyleRange nextRange = findNextRange();
				if (nextRange == null) {
					styledText.setSelection(0);
				} else {
					styledText.setSelectionRange(nextRange.start, nextRange.length);
					e.detail = SWT.TRAVERSE_NONE;
				}
				e.doit = true;
				break;
			case SWT.TRAVERSE_TAB_PREVIOUS:
				Point previousSelection = styledText.getSelection();
				if ((previousSelection.x == 0) && (previousSelection.y == 0)) {
					styledText.setSelection(styledText.getCharCount());
				}
				StyleRange previousRange = findPreviousRange();
				if (previousRange == null) {
					styledText.setSelection(styledText.getCharCount());
				} else {
					styledText.setSelectionRange(previousRange.start, previousRange.length);
					e.detail = SWT.TRAVERSE_NONE;

        styledText.addMouseMoveListener(e -> {
		    if (mouseDown) {
		        if (!dragEvent) {
		            StyledText text1 = (StyledText) e.widget;
		            text1.setCursor(null);
		        }
		        dragEvent = true;
		        return;
		    }
		    StyledText text2 = (StyledText) e.widget;
		    int offset = -1;
		    try {
		        offset = text2.getOffsetAtLocation(new Point(e.x, e.y));
		    } catch (IllegalArgumentException ex) {
		    }
		    if (offset == -1) {
				text2.setCursor(null);
			} else if (item != null && item.isLinkAt(offset)) {
				text2.setCursor(handCursor);
			} else {
				text2.setCursor(null);
			}
		});

        styledText.addTraverseListener(e -> {
		    switch (e.detail) {
		    case SWT.TRAVERSE_ESCAPE:
		        e.doit = true;
		        break;
		    case SWT.TRAVERSE_TAB_NEXT:
		        Point nextSelection = styledText.getSelection();
		        int charCount = styledText.getCharCount();
		        if ((nextSelection.x == charCount)
		                && (nextSelection.y == charCount)) {
		        	styledText.setSelection(0);
		        }
		        StyleRange nextRange = findNextRange();
		        if (nextRange == null) {
		        	styledText.setSelection(0);
		            e.doit = true;
		        } else {
		        	styledText.setSelectionRange(nextRange.start,
		                    nextRange.length);
		            e.doit = true;
		            e.detail = SWT.TRAVERSE_NONE;
		        }
		        break;
		    case SWT.TRAVERSE_TAB_PREVIOUS:
		        Point previousSelection = styledText.getSelection();
		        if ((previousSelection.x == 0)
		                && (previousSelection.y == 0)) {
		        	styledText.setSelection(styledText.getCharCount());

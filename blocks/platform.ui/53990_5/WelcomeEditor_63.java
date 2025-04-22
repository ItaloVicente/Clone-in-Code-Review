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
		    WelcomeItem item = (WelcomeItem) e.widget.getData();
		    int offset = -1;
		    try {
		        offset = text2.getOffsetAtLocation(new Point(e.x, e.y));
		    } catch (IllegalArgumentException ex) {
		    }
		    if (offset == -1) {
				text2.setCursor(null);
			} else if (item.isLinkAt(offset)) {
				text2.setCursor(handCursor);
			} else {
				text2.setCursor(null);
			}
		});

        styledText.addTraverseListener(e -> {
		    StyledText text = (StyledText) e.widget;

		    switch (e.detail) {
		    case SWT.TRAVERSE_ESCAPE:
		        e.doit = true;
		        break;
		    case SWT.TRAVERSE_TAB_NEXT:
		        if ((e.stateMask & SWT.CTRL) != 0) {
		            if (e.widget == lastText) {

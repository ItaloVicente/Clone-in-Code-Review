        styledText.addMouseMoveListener(new MouseMoveListener() {
            @Override
			public void mouseMove(MouseEvent e) {
                if (mouseDown) {
                    if (!dragEvent) {
                        StyledText text = (StyledText) e.widget;
                        text.setCursor(null);
                    }
                    dragEvent = true;
                    return;
                }
                StyledText text = (StyledText) e.widget;
                WelcomeItem item = (WelcomeItem) e.widget.getData();
                int offset = -1;
                try {
                    offset = text.getOffsetAtLocation(new Point(e.x, e.y));
                } catch (IllegalArgumentException ex) {
                }
                if (offset == -1) {
					text.setCursor(null);
				} else if (item.isLinkAt(offset)) {
					text.setCursor(handCursor);
				} else {
					text.setCursor(null);
				}
            }
        });

        styledText.addTraverseListener(new TraverseListener() {
            @Override
			public void keyTraversed(TraverseEvent e) {
                StyledText text = (StyledText) e.widget;

                switch (e.detail) {
                case SWT.TRAVERSE_ESCAPE:
                    e.doit = true;
                    break;
                case SWT.TRAVERSE_TAB_NEXT:
                    if ((e.stateMask & SWT.CTRL) != 0) {
                        if (e.widget == lastText) {
							return;
						}
						e.doit = false;
						nextTabAbortTraversal = true;
						lastText.traverse(SWT.TRAVERSE_TAB_NEXT);

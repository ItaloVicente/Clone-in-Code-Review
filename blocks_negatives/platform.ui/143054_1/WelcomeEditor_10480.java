                mouseDown = false;
                StyledText text = (StyledText) e.widget;
                WelcomeItem item = (WelcomeItem) e.widget.getData();
                int offset = text.getCaretOffset();
                if (dragEvent) {
                    dragEvent = false;
                    if (item.isLinkAt(offset)) {
                        text.setCursor(handCursor);
                    }
                } else if (item.isLinkAt(offset)) {
                    text.setCursor(busyCursor);
                    if (e.button == 1) {
                        item.triggerLinkAt(offset);
                        StyleRange selectionRange = getCurrentLink(text);
                        text.setSelectionRange(selectionRange.start,
                                selectionRange.length);
                        text.setCursor(null);
                    }
                }
            }
        });

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

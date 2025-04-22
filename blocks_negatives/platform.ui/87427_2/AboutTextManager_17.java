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
                int offset = -1;
                try {
                    offset = text.getOffsetAtLocation(new Point(e.x, e.y));
                } catch (IllegalArgumentException ex) {
                }
                if (offset == -1) {
					text.setCursor(null);
				} else if (item != null && item.isLinkAt(offset)) {
					text.setCursor(handCursor);
				} else {
					text.setCursor(null);

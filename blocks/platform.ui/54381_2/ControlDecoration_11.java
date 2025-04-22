		mouseMoveListener = event -> {
			if (showHover) {
				if (!decorationRectangle.contains(event.x, event.y)) {
					hideHover();
					printRemoveListener(event.widget, "MOUSEMOVE"); //$NON-NLS-1$
					((Control) event.widget)
							.removeMouseMoveListener(mouseMoveListener);
					moveListeningTarget = null;

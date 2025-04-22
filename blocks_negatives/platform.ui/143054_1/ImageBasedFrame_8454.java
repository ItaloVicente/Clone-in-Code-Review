		addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				Rectangle handleRect = getHandleRect();

				ImageBasedFrame frame = (ImageBasedFrame) e.widget;
				if (handleRect.contains(e.x, e.y)) {
					frame.setCursor(frame.getDisplay().getSystemCursor(
							SWT.CURSOR_SIZEALL));
				} else {
					frame.setCursor(null);
				}

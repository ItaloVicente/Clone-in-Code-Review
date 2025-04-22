		textWidget.addListener(SWT.MouseMove, new Listener() {
			public void handleEvent(final Event e) {
				StyleRange styleRange = getStyleRange(e.x, e.y);
				if (styleRange != null && styleRange.underline)
					textWidget.setCursor(SYS_LINK_CURSOR);
				else
					textWidget.setCursor(sys_normalCursor);
			}
		});


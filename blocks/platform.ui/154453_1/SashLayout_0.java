		host.addMouseTrackListener(MouseTrackListener.mouseEnterAdapter((e -> {
			List<SashRect> sashList = getSashRects(e.x, e.y);
			if (sashList.isEmpty()) {
				host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
			} else if (sashList.size() == 1) {
				if (sashList.get(0).container.isHorizontal())
					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZEWE));
				else
					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZENS));
			} else {
				host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL));
			}
		})));

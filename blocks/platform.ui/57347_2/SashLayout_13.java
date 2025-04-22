		host.addMouseMoveListener(e -> {
			if (!draggingSashes) {
				List<SashRect> sashList = getSashRects(e.x, e.y);
				if (sashList.size() == 0) {
					host.setCursor(host.getDisplay().getSystemCursor(
							SWT.CURSOR_ARROW));
				} else if (sashList.size() == 1) {
					if (sashList.get(0).container.isHorizontal())

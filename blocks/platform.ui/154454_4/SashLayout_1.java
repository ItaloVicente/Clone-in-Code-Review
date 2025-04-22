	private void onMouseMove(MouseEvent e) {
		if (!draggingSashes) {
			List<SashRect> sashList = getSashRects(e.x, e.y);
			final int newCursor;
			if (sashList.isEmpty()) {
				newCursor = SWT.CURSOR_ARROW;
			} else if (sashList.size() == 1) {
				if (sashList.get(0).container.isHorizontal()) {
					newCursor = SWT.CURSOR_SIZEWE;
				} else {
					newCursor = SWT.CURSOR_SIZENS;
				}
			} else {
				newCursor = SWT.CURSOR_SIZEALL;
			}
			if (lastCursor != newCursor) {
				host.setCursor(host.getDisplay().getSystemCursor(newCursor));
				lastCursor = newCursor;
			}
		} else {
			try {
				layoutUpdateInProgress = true;
				adjustWeights(sashesToDrag, e.x, e.y);
				try {
					host.setRedraw(false);
					host.layout();
				} finally {
					host.setRedraw(true);
				}
				host.update();
			} finally {
				layoutUpdateInProgress = false;
			}
		}
	}


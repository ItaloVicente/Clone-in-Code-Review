		host.addMouseTrackListener(MouseTrackListener.mouseExitAdapter(e -> host.setCursor(null)));

		host.addMouseMoveListener(e -> {
			if (!draggingSashes) {
				List<SashRect> sashList = getSashRects(e.x, e.y);
				if (sashList.isEmpty()) {
					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				} else if (sashList.size() == 1) {
					if (sashList.get(0).container.isHorizontal())
						host.setCursor(host.getDisplay().getSystemCursor(
								SWT.CURSOR_SIZEWE));
					else
						host.setCursor(host.getDisplay().getSystemCursor(
								SWT.CURSOR_SIZENS));
				} else {
					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL));
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
		});

		host.addMouseTrackListener(MouseTrackListener.mouseExitAdapter(e -> {
			host.setCursor(null);
			lastCursor = 0;
		}));

		host.addMouseMoveListener(this::onMouseMove);

		host.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				host.setCapture(false);
				draggingSashes = false;
			}

			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button != 1) {
					return;
				}

				sashesToDrag = getSashRects(e.x, e.y);
				if (sashesToDrag.size() > 0) {
					draggingSashes = true;
					host.setCapture(true);
				}
			}

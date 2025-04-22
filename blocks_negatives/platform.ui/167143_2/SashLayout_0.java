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

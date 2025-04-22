		host.addMouseTrackListener(MouseTrackListener.mouseExitAdapter(e -> {
			host.setCursor(null);
			lastCursor = 0;
		}));

		host.addMouseMoveListener(this::onMouseMove);

		Listener mouseMoveListener = this::onMouseMove;
		Listener mouseExitListener = e -> {
				host.setCursor(null);
				lastCursor = 0;
		};
		Listener mouseUpListener = e -> {
			host.setCapture(false);
			draggingSashes = false;
		};
		Listener mouseDownListener = e -> {
			if (e.button != 1 || !(e.widget instanceof Control)) {
				return;

		bar.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				if (dragging) {
					track(e);
				}

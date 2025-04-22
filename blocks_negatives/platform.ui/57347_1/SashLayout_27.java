		host.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(final MouseEvent e) {
				if (!draggingSashes) {
					List<SashRect> sashList = getSashRects(e.x, e.y);
					if (sashList.size() == 0) {

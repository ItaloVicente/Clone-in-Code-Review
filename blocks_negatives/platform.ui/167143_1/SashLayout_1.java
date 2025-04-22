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

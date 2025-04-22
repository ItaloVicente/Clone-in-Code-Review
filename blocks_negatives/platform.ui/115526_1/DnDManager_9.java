			overlayFrame.addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent e) {
					for (int i = 0; i < images.size(); i++) {
						Image image = images.get(i);
						Rectangle iRect = imageRects.get(i);
						e.gc.drawImage(image, iRect.x, iRect.y);
					}

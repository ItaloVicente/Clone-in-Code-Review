	private void addDropFeedback(Rectangle itemBounds) {
		Image img = getDropImage();
		Rectangle imgBounds = img.getBounds();
		imgBounds.x = itemBounds.x - imgBounds.width / 2;
		imgBounds.y = itemBounds.y - imgBounds.height / 2;
		dndManager.clearOverlay();
		dndManager.addImage(imgBounds, img);
	}

	public Image getDropImage() {
		if (dropImage == null) {
			Display curDisplay = Display.getCurrent();
			dropImage = new Image(curDisplay, 16, 16);
			GC gc = new GC(dropImage);
			int[] pts = new int[] { 1, 1, 15, 0, 8, 15 };
			gc.setBackground(curDisplay.getSystemColor(SWT.COLOR_GRAY));
			gc.fillPolygon(pts);
			gc.setForeground(curDisplay.getSystemColor(SWT.COLOR_BLACK));
			gc.drawPolygon(pts);
			gc.dispose();
		}
		return dropImage;
	}


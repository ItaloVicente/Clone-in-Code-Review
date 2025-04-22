
	}


	private void repaint(GC gc, int x, int y, int width, int height) {
		Image textBuffer = new FormTextImageDescriptor(width, height).createImage(getDisplay());

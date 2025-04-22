		Image textBuffer = new FormTextImageDescriptor(gc.getStyle(), width, height).createImage(getDisplay());
		if (hasFocus && !model.hasFocusSegments())
			gc.drawFocus(x, y, width, height);
		gc.drawImage(textBuffer, x, y);
		textBuffer.dispose();
	}
	
	private void doRepaintOnImageGC(GC imageGC, int x, int y, int width, int height) {

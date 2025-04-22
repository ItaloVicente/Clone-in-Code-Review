		GC gc = new GC(getContents());
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();
		int x = Dialog.convertHorizontalDLUsToPixels(fontMetrics, 300);
		if (x < 350) {
			x = 350;
		}
		int y = Dialog.convertVerticalDLUsToPixels(fontMetrics, 270);
		if (y < 420) {
			y = 420;
		}
		return new Point(x, y);

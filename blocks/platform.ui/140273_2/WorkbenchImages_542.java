	private static final void declareImage(String key, String path, boolean shared) {
		URL url = BundleUtility.find(PlatformUI.PLUGIN_ID, path);
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		declareImage(key, desc, shared);
	}

	private static void drawViewMenu(GC gc, GC maskgc) {
		Display display = Display.getCurrent();

		gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));

		int[] shapeArray = new int[] { 1, 1, 10, 1, 6, 5, 5, 5 };
		gc.fillPolygon(shapeArray);
		gc.drawPolygon(shapeArray);

		Color black = display.getSystemColor(SWT.COLOR_BLACK);
		Color white = display.getSystemColor(SWT.COLOR_WHITE);

		maskgc.setBackground(black);
		maskgc.fillRectangle(0, 0, 12, 16);

		maskgc.setBackground(white);
		maskgc.setForeground(white);
		maskgc.fillPolygon(shapeArray);
		maskgc.drawPolygon(shapeArray);
	}

	private static final void declareImages() {

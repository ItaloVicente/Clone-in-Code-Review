    /**
     * Declares a workbench image given the path of the image file (relative to
     * the workbench plug-in). This is a helper method that creates the image
     * descriptor and passes it to the main <code>declareImage</code> method.
     *
     * @param key the symbolic name of the image
     * @param path the path of the image file relative to the base of the workbench
     * plug-ins install directory
     * @param shared <code>true</code> if this is a shared image, and
     * <code>false</code> if this is not a shared image
     */
    private static final void declareImage(String key, String path,
            boolean shared) {
        URL url = BundleUtility.find(PlatformUI.PLUGIN_ID, path);
        ImageDescriptor desc = ImageDescriptor.createFromURL(url);
        declareImage(key, desc, shared);
    }

    private static void drawViewMenu(GC gc, GC maskgc) {
    	Display display = Display.getCurrent();

    	gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
    	gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));

	    int[] shapeArray = new int[] {1, 1, 10, 1, 6, 5, 5, 5};
	    gc.fillPolygon(shapeArray);
	    gc.drawPolygon(shapeArray);

	    Color black = display.getSystemColor(SWT.COLOR_BLACK);
	    Color white = display.getSystemColor(SWT.COLOR_WHITE);

	    maskgc.setBackground(black);
	    maskgc.fillRectangle(0,0,12,16);

	    maskgc.setBackground(white);
	    maskgc.setForeground(white);
	    maskgc.fillPolygon(shapeArray);
	    maskgc.drawPolygon(shapeArray);
    }

    /**
     * Declares all the workbench's images, including both "shared" ones and
     * internal ones.
     */
    private static final void declareImages() {

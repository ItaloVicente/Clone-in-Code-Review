
		Display d = Display.getCurrent();

		Image viewMenu = new Image(d, 11, 16);
		Image viewMenuMask = new Image(d, 11, 16);

		GC gc = new GC(viewMenu);
		GC maskgc = new GC(viewMenuMask);
		drawViewMenu(gc, maskgc);
		gc.dispose();
		maskgc.dispose();

		ImageData data = viewMenu.getImageData();
		data.transparentPixel = data.getPixel(0, 0);

		Image vm2 = new Image(d, viewMenu.getImageData(), viewMenuMask.getImageData());
		viewMenu.dispose();
		viewMenuMask.dispose();

		getImageRegistry().put(IWorkbenchGraphicConstants.IMG_LCL_RENDERED_VIEW_MENU, vm2);

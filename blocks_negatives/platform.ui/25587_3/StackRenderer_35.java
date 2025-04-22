			Display d = Display.getCurrent();

			Image viewMenu = new Image(d, 16, 16);
			Image viewMenuMask = new Image(d, 16, 16);

			Display display = Display.getCurrent();
			GC gc = new GC(viewMenu);
			GC maskgc = new GC(viewMenuMask);
			gc.setForeground(display
					.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));

			int[] shapeArray = new int[] { 6, 3, 15, 3, 11, 7, 10, 7 };
			gc.fillPolygon(shapeArray);
			gc.drawPolygon(shapeArray);

			Color black = display.getSystemColor(SWT.COLOR_BLACK);
			Color white = display.getSystemColor(SWT.COLOR_WHITE);

			maskgc.setBackground(black);
			maskgc.fillRectangle(0, 0, 16, 16);

			maskgc.setBackground(white);
			maskgc.setForeground(white);
			maskgc.fillPolygon(shapeArray);
			maskgc.drawPolygon(shapeArray);
			gc.dispose();
			maskgc.dispose();

			ImageData data = viewMenu.getImageData();
			data.transparentPixel = data.getPixel(0, 0);

			viewMenuImage = new Image(d, viewMenu.getImageData(),
					viewMenuMask.getImageData());
			viewMenu.dispose();
			viewMenuMask.dispose();

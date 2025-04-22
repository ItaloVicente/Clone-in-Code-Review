		private static int IMAGE_SIZE = 16;
		private static final Image IMAGE1 = new Image(DISPLAY, DISPLAY
				.getSystemImage(SWT.ICON_WARNING).getImageData()
				.scaledTo(IMAGE_SIZE, IMAGE_SIZE));
		private static final Image IMAGE2 = new Image(DISPLAY, DISPLAY
				.getSystemImage(SWT.ICON_ERROR).getImageData()
				.scaledTo(IMAGE_SIZE, IMAGE_SIZE));

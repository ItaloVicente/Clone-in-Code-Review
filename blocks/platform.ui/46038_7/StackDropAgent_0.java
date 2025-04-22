		Display display = Display.getCurrent();
		imagesHeight = 16;
		images = Arrays.asList(new Image(display, 12, 2), new Image(display, 10, 2), new Image(display, 8, 2),
				new Image(display, 6, 2), new Image(display, 4, 2), new Image(display, 2, 2));
	}

	@Override
	public void dispose() {
		for (Image img : images) {
			if (!img.isDisposed()) {
				img.dispose();
			}
		}
		super.dispose();

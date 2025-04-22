	private void disposeImages() {
		for (Image image : images) {
			image.dispose();
		}
		images = null;
	}


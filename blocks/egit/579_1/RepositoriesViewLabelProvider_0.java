	@Override
	public void dispose() {
		for (Image image : decoratedImages.values()) {
			image.dispose();
		}
		decoratedImages.clear();
		super.dispose();
	}


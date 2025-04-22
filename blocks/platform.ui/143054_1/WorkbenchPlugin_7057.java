	}

	public ISharedImages getSharedImages() {
		if (sharedImages == null) {
			sharedImages = new SharedImages();
		}
		if (e4Context == null) {
			return sharedImages;
		}

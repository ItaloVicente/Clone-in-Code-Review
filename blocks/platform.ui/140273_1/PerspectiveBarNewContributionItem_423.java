		super.dispose();
		if (image != null && !image.isDisposed()) {
			image.dispose();
			image = null;
		}
	}

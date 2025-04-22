		super.dispose();
		if (missingImage != null) {
			missingImage.dispose();
			missingImage = null;
		}
	}

	}

		synchronized (imageDescLock) {
			imageDesc = desc;
			testImage = true;
		}
	}

	public String getImageFilename() {
		if (configurationElement == null) {

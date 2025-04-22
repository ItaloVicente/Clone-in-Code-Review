		ImageResourceManager imageManager = new ImageResourceManager(this);

		for (int i = 0; i < 13; i++) {
			ImageDescriptor id = ImageResourceManager.
					getImageDescriptor(URL_BUSY + (i + 1) + ".gif"); //$NON-NLS-1$

			images[i] = imageManager.getImage(id);
		}

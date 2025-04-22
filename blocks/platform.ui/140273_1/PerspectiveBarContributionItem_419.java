		super.dispose();
		if (image != null && !image.isDisposed()) {
			image.dispose();
			image = null;
		}
		apiPreferenceStore = null;
		workbenchPage = null;
		perspective = null;

	}

	@Override

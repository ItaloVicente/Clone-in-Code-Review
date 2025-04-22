		}

		return null;
	}

	public ImageDescriptor getAboutImage() {
		return productProperties == null ? null : productProperties
				.getAboutImage();
	}

	public ImageDescriptor getFeatureImage() {
		return bundleGroupProperties == null ? null : bundleGroupProperties
				.getFeatureImage();
	}

	public String getFeatureImageName() {
		if (bundleGroupProperties == null) {

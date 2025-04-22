		return null;
	}

	public String getWelcomePerspectiveId() {
		return bundleGroupProperties == null ? null : bundleGroupProperties
				.getWelcomePerspective();
	}

	public String getTipsAndTricksHref() {
		return bundleGroupProperties == null ? null : bundleGroupProperties
				.getTipsAndTricksHref();
	}

	public ImageDescriptor[] getWindowImages() {
		return productProperties == null ? null : productProperties
				.getWindowImages();
	}

	public String getBrandingBundleId() {
		return bundleGroupProperties == null ? null : bundleGroupProperties
				.getBrandingBundleId();
	}

	public String getBrandingBundleVersion() {
		return bundleGroupProperties == null ? null : bundleGroupProperties
				.getBrandingBundleVersion();
	}

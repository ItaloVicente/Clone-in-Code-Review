	public static ImageDescriptor getFeatureImage(IBundleGroup bundleGroup) {
		return getImage(bundleGroup.getProperty(FEATURE_IMAGE), null);
	}

	public static URL getFeatureImageUrl(IBundleGroup bundleGroup) {
		return getUrl(bundleGroup.getProperty(FEATURE_IMAGE), null);
	}

	public static String getTipsAndTricksHref(IBundleGroup bundleGroup) {
		return bundleGroup.getProperty(TIPS_AND_TRICKS_HREF);
	}

	public static URL getWelcomePageUrl(IBundleGroup bundleGroup) {
		return getUrl(bundleGroup.getProperty(WELCOME_PAGE), null);
	}

	public static String getWelcomePerspective(IBundleGroup bundleGroup) {
		String property = bundleGroup.getProperty(WELCOME_PERSPECTIVE);
		return property == null ? null : property;
	}

	public static URL getLicenseUrl(IBundleGroup bundleGroup) {
		return getUrl(bundleGroup.getProperty(LICENSE_HREF), null);
	}

	public static String getFeatureLabel(IBundleGroup bundleGroup) {
		return bundleGroup.getName();
	}

	public static String getFeatureId(IBundleGroup bundleGroup) {
		return bundleGroup.getIdentifier();
	}

	public static String getProviderName(IBundleGroup bundleGroup) {
		return bundleGroup.getProviderName();
	}

	public static String getFeatureVersion(IBundleGroup bundleGroup) {
		return bundleGroup.getVersion();
	}

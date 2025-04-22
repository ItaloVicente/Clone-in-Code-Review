    /**
     * An image which can be shown in an "about features" dialog (32x32).
     */
    public static ImageDescriptor getFeatureImage(IBundleGroup bundleGroup) {
        return getImage(bundleGroup.getProperty(FEATURE_IMAGE), null);
    }

    /**
     * The URL to an image which can be shown in an "about features" dialog (32x32).
     */
    public static URL getFeatureImageUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(FEATURE_IMAGE), null);
    }

    /**
     * A help reference for the feature's tips and tricks page (optional).
     */
    public static String getTipsAndTricksHref(IBundleGroup bundleGroup) {
        return bundleGroup.getProperty(TIPS_AND_TRICKS_HREF);
    }

    /**
     * A URL for the feature's welcome page (special XML-based format) ($nl$/
     * prefix to permit locale-specific translations of entire file). Products
     * designed to run "headless" typically would not have such a page.
     */
    public static URL getWelcomePageUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(WELCOME_PAGE), null);
    }

    /**
     * The id of a perspective in which to show the welcome page (optional).
     */
    public static String getWelcomePerspective(IBundleGroup bundleGroup) {
        String property = bundleGroup.getProperty(WELCOME_PERSPECTIVE);
        return property == null ? null : property;
    }

    /**
     * A URL for the feature's license page.
     */
    public static URL getLicenseUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(LICENSE_HREF), null);
    }

    /**
     * Returns a label for the feature plugn, or <code>null</code>.
     */
    public static String getFeatureLabel(IBundleGroup bundleGroup) {
        return bundleGroup.getName();
    }

    /**
     * Returns the id for this bundleGroup.
     */
    public static String getFeatureId(IBundleGroup bundleGroup) {
        return bundleGroup.getIdentifier();
    }

    /**
     * Returns the provider name.
     */
    public static String getProviderName(IBundleGroup bundleGroup) {
        return bundleGroup.getProviderName();
    }

    /**
     * Returns the feature version id.
     */
    public static String getFeatureVersion(IBundleGroup bundleGroup) {
        return bundleGroup.getVersion();
    }

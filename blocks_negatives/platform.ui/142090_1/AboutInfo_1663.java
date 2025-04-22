        URL url = bundleGroupProperties.getFeatureImageUrl();
        return url == null ? null : new Path(url.getPath()).lastSegment();
    }

    /**
     * Returns the CRC of the feature image as supplied in the properties file.
     *
     * @return the CRC of the feature image, or <code>null</code> if none
     */
    public Long getFeatureImageCRC() {
        if (bundleGroupProperties == null) {

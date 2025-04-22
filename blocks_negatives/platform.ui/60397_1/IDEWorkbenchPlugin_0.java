
    /**
     * Returns the about information of the primary feature.
     *
     * @return info about the primary feature, or <code>null</code> if there
     * is no primary feature or if this information is unavailable
     */
    public AboutInfo getPrimaryInfo() {
        IProduct product = Platform.getProduct();
        return product == null ? null : new AboutInfo(product);
    }


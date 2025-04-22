    }

    /**
     * The text to show in an "about" dialog for this product.
     * Products designed to run "headless" typically would not
     * have such text.
     * <p>
     * The returned value will have {n} values substituted based on the
     * current product's mappings regardless of the given product argument.
     * </p>
     */
    public static String getAboutText(IProduct product) {
        String property = product.getProperty(ABOUT_TEXT);
        if (property == null) {

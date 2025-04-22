        return getImages(property, product.getDefiningBundle());
    }

    /**
     * Location of the product's welcome page (special XML-based format), either
     * a fully qualified valid URL or a path relative to the product's defining
     * bundle. Products designed to run "headless" typically would not have such
     * a page. Use of this property is discouraged in 3.0, the new
     * org.eclipse.ui.intro extension point should be used instead.
     */
    public static URL getWelcomePageUrl(IProduct product) {
        return getUrl(product.getProperty(WELCOME_PAGE), product
                .getDefiningBundle());
    }

    /**
     * Returns the product name or <code>null</code>.
     * This is shown in the window title and the About action.
     */
    public static String getProductName(IProduct product) {
        return product.getName();
    }

    /**
     * Returns the id for the product.
     */
    public static String getProductId(IProduct product) {
        return product.getId();
    }

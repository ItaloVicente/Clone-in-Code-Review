        return windowImageDescriptors;
    }

    /**
     * Location of the product's welcome page (special XML-based format), either
     * a fully qualified valid URL or a path relative to the product's defining
     * bundle. Products designed to run "headless" typically would not have such
     * a page. Use of this property is discouraged in 3.0, the new
     * org.eclipse.ui.intro extension point should be used instead.
     */
    public URL getWelcomePageUrl() {
        if (welcomePageUrl == null) {

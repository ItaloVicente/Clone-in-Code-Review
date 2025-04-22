        return appName;
    }

    /**
     * The text to show in an "about" dialog for this product.
     * Products designed to run "headless" typically would not
     * have such text.
     */
    public String getAboutText() {
        if (aboutText == null) {

    /**
     * Returns the text to show in an "about" dialog for this product.
     * Products designed to run "headless" typically would not have such text.
     *
     * @return the about text, or <code>null</code> if none
     */
    public String getAboutText() {
        if (aboutText == null && product != null) {

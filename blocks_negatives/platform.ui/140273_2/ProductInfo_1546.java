    /**
     * Returns the product name or <code>null</code>.
     * This is shown in the window title and the About action.
     *
     * @return the product name, or <code>null</code>
     */
    public String getProductName() {
        if (productName == null && product != null) {

        return isValid;
    }

    /**
     * Suppresses creation of the standard Default and Apply buttons
     * for this page.
     * <p>
     * Subclasses wishing a preference page without these buttons
     * should call this framework method before the page's control
     * has been created.
     * </p>
     */
    protected void noDefaultAndApplyButton() {

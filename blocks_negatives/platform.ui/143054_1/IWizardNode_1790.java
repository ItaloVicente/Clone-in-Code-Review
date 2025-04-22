    /**
     * Returns the wizard this node stands for.
     * <p>
     * If the content has not been created beforehand, calling this
     * method triggers the creation of the wizard and caches it so that
     * the identical wizard object is returned on subsequent calls.
     * </p>
     *
     * @return the wizard
     */
    public IWizard getWizard();

    /**
     * Returns the site for this workbench part. The site can be
     * <code>null</code> while the workbench part is being initialized. After
     * the initialization is complete, this value must be non-<code>null</code>
     * for the remainder of the part's life cycle.
     *
     * @return The part site; this value may be <code>null</code> if the part
     *         has not yet been initialized
     */
    IWorkbenchPartSite getSite();

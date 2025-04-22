    /**
     * Returns the site for this view.
     * This method is equivalent to <code>(IViewSite) getSite()</code>.
     * <p>
     * The site can be <code>null</code> while the view is being initialized.
     * After the initialization is complete, this value must be non-<code>null</code>
     * for the remainder of the view's life cycle.
     * </p>
     *
     * @return the view site; this value may be <code>null</code> if the view
     *         has not yet been initialized
     */
    public IViewSite getViewSite();

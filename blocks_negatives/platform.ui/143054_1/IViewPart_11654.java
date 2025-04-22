    /**
     * Initializes this view with the given view site.
     * <p>
     * This method is automatically called by the workbench shortly after the
     * part is instantiated.  It marks the start of the views's lifecycle. Clients must
     * not call this method.
     * </p>
     *
     * @param site the view site
     * @exception PartInitException if this view was not initialized successfully
     */
    public void init(IViewSite site) throws PartInitException;

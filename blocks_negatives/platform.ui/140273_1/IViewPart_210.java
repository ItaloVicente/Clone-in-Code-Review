    /**
     * Initializes this view with the given view site.  A memento is passed to
     * the view which contains a snapshot of the views state from a previous
     * session.  Where possible, the view should try to recreate that state
     * within the part controls.
     * <p>
     * This method is automatically called by the workbench shortly after the part
     * is instantiated.  It marks the start of the views's lifecycle. Clients must
     * not call this method.
     * </p>
     *
     * @param site the view site
     * @param memento the IViewPart state or null if there is no previous saved state
     * @exception PartInitException if this view was not initialized successfully
     */
    void init(IViewSite site, IMemento memento) throws PartInitException;

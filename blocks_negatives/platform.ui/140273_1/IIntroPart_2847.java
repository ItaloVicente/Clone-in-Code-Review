    /**
     * Initializes this intro part with the given intro site. A memento is
     * passed to the part which contains a snapshot of the part state from a
     * previous session. Where possible, the part should try to recreate that
     * state.
     * <p>
     * This method is automatically called by the workbench shortly after
     * part construction.  It marks the start of the intro's lifecycle. Clients
     * must not call this method.
     * </p>
     *
     * @param site the intro site
     * @param memento the intro part state or <code>null</code> if there is no previous
     * saved state
     * @exception PartInitException if this part was not initialized
     * successfully
     */
    void init(IIntroSite site, IMemento memento)
            throws PartInitException;

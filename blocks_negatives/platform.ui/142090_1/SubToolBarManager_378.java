    /**
     * Constructs a new manager.
     *
     * @param mgr the parent manager.  All contributions made to the
     *      <code>SubToolBarManager</code> are forwarded and appear in the
     *      parent manager.
     */
    public SubToolBarManager(IToolBarManager mgr) {
        super(mgr);
    }

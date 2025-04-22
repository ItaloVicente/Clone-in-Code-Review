    /**
     * The menu listener added to the parent.  Lazily initialized
     * in addMenuListener.
     */
    private IMenuListener menuListener;

    /**
     * Constructs a new manager.
     *
     * @param mgr the parent manager.  All contributions made to the
     *      <code>SubMenuManager</code> are forwarded and appear in the
     *      parent manager.
     */
    public SubMenuManager(IMenuManager mgr) {
        super(mgr);
    }

    @Override

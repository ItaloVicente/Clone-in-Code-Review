    /**
     * Constructs a new manager.
     *
     * @param mgr the parent manager.  All contributions made to the
     *      <code>SubCoolBarManager</code> are forwarded and appear in the
     *      parent manager.
     */
    public SubCoolBarManager(ICoolBarManager mgr) {
        super(mgr);
        Assert.isNotNull(mgr);
    }

        IStatusLineManager {
    /**
     * Current status line message.
     */
    private String message;

    /**
     * Current status line error message.
     */
    private String errorMessage;

    /**
     * Current status line message image.
     */
    private Image messageImage;

    /**
     * Current status line error image
     */
    private Image errorImage;

    /**
     * Constructs a new manager.
     *
     * @param mgr the parent manager.  All contributions made to the
     *      <code>SubStatusLineManager</code> are forwarded and appear in the
     *      parent manager.
     */
    public SubStatusLineManager(IStatusLineManager mgr) {
        super(mgr);
    }

    /**
     * @return the parent status line manager that this sub-manager contributes
     * to
     */
    protected final IStatusLineManager getParentStatusLineManager() {
        return (IStatusLineManager) getParent();
    }

    @Override

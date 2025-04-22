    /**
     * Creates an empty window manager with the given
     * window manager as parent.
     *
     * @param parent the parent window manager
     */
    public WindowManager(WindowManager parent) {
        Assert.isNotNull(parent);
        parent.addWindowManager(this);
    }

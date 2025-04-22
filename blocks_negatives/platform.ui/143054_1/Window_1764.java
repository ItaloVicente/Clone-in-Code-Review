    /**
     * Creates a new window which will create its shell as a child of whatever
     * the given shellProvider returns.
     *
     * @param shellProvider object that will return the current parent shell. Not null.
     *
     * @since 3.1
     */
    protected Window(IShellProvider shellProvider) {
        Assert.isNotNull(shellProvider);
        this.parentShell = shellProvider;
    }

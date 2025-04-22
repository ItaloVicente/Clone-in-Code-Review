    /**
     * Returns a shell provider that always returns the current
     * shell for the given control.
     *
     * @param targetControl control whose shell will be tracked, or null if getShell() should always
     * return null
     */
    public SameShellProvider(Control targetControl) {
        this.targetControl = targetControl;
    }

     * Changes the parent shell. This is only safe to use when the shell is not
     * yet realized (i.e., created). Once the shell is created, it must be
     * disposed (i.e., closed) before this method can be called.
     *
     * @param newParentShell
     *            The new parent shell; this value may be <code>null</code> if
     *            there is to be no parent.
     * @since 3.1
     */
    protected void setParentShell(final Shell newParentShell) {
        Assert.isTrue((shell == null), "There must not be an existing shell."); //$NON-NLS-1$
        parentShell = new SameShellProvider(newParentShell);
    }

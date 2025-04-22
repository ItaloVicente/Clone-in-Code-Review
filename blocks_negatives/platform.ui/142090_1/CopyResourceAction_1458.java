    /**
     * Returns the shell in which to show any dialogs
     * @return The shell for parenting dialogs; never <code>null</code>.
     */
    Shell getShell() {
        return shellProvider.getShell();
    }

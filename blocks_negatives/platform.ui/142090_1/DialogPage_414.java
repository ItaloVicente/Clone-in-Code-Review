        return messageType;
    }

    /**
     * Returns this dialog page's shell. Convenience method for
     * <code>getControl().getShell()</code>. This method may only be called
     * after the page's control has been created.
     *
     * @return the shell
     */
    public Shell getShell() {
        return getControl().getShell();
    }

    @Override

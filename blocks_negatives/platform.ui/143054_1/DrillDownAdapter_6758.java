    /**
     * Returns whether "go home" is possible for child tree.  This is only possible
     * if the client has performed one or more drilling operations.
     *
     * @return <code>true</code> if "go home" is possible; <code>false</code> otherwise
     */
    public boolean canGoHome() {
        return fDrillStack.canGoHome();
    }

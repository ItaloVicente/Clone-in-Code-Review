    /**
     * Returns whether "go back" is possible for child tree.  This is only possible
     * if the client has performed one or more drilling operations.
     *
     * @return <code>true</code> if "go back" is possible; <code>false</code> otherwise
     */
    public boolean canGoBack() {
        return fDrillStack.canGoBack();
    }

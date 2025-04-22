    public ICommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Returns the set of identifiers to previously defined categories.
     *
     * @return the set of identifiers to previously defined categories. This set
     *         may be empty. If this set is not empty, it is guaranteed to only
     *         contain instances of <code>String</code>. This set is
     *         guaranteed to be <code>null</code> if
     *         haveDefinedCategoryIdsChanged() is <code>false</code> and is
     *         guaranteed to not be null if haveDefinedCategoryIdsChanged() is
     *         <code>true</code>.
     */

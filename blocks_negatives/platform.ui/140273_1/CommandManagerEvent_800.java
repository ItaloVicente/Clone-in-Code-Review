    public Set getPreviouslyDefinedCategoryIds() {
        return previouslyDefinedCategoryIds;
    }

    /**
     * Returns the set of identifiers to previously defined commands.
     *
     * @return the set of identifiers to previously defined commands. This set
     *         may be empty. If this set is not empty, it is guaranteed to only
     *         contain instances of <code>String</code>. This set is
     *         guaranteed to be <code>null</code> if
     *         haveDefinedCommandIdsChanged() is <code>false</code> and is
     *         guaranteed to not be null if haveDefinedCommandIdsChanged() is
     *         <code>true</code>.
     */

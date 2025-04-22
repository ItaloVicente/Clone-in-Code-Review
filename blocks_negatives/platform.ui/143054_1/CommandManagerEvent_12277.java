    public Set getPreviouslyDefinedCommandIds() {
        return previouslyDefinedCommandIds;
    }

    /**
     * Returns the set of identifiers to previously defined key conigurations.
     *
     * @return the set of identifiers to previously defined key configurations.
     *         This set may be empty. If this set is not empty, it is guaranteed
     *         to only contain instances of <code>String</code>. This set is
     *         guaranteed to be <code>null</code> if
     *         haveDefinedKeyConfigurationIdsChanged() is <code>false</code>
     *         and is guaranteed to not be null if
     *         haveDefinedKeyConfigurationIdsChanged() is <code>true</code>.
     */

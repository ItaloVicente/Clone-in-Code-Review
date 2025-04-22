        if (definedContextIdsChanged) {
            this.previouslyDefinedContextIds = Util.safeCopy(
                    previouslyDefinedContextIds, String.class);
        } else {
            this.previouslyDefinedContextIds = null;
        }

        if (enabledContextIdsChanged) {
            this.previouslyEnabledContextIds = Util.safeCopy(
                    previouslyEnabledContextIds, String.class);
        } else {
            this.previouslyEnabledContextIds = null;
        }

        this.contextManager = contextManager;
        this.definedContextIdsChanged = definedContextIdsChanged;
        this.enabledContextIdsChanged = enabledContextIdsChanged;
    }

    /**
     * Returns the instance of the interface that changed.
     *
     * @return the instance of the interface that changed. Guaranteed not to be
     *         <code>null</code>.
     */
    public IContextManager getContextManager() {
        return contextManager;
    }

    /**
     * Returns the set of identifiers to previously defined contexts.
     *
     * @return the set of identifiers to previously defined contexts. This set
     *         may be empty. If this set is not empty, it is guaranteed to only
     *         contain instances of <code>String</code>. This set is
     *         guaranteed to be <code>null</code> if
     *         haveDefinedContextIdsChanged() is <code>false</code> and is
     *         guaranteed to not be null if haveDefinedContextIdsChanged() is
     *         <code>true</code>.
     */
    public Set getPreviouslyDefinedContextIds() {
        return previouslyDefinedContextIds;
    }

    /**
     * Returns the set of identifiers to previously enabled contexts.
     *
     * @return the set of identifiers to previously enabled contexts. This set
     *         may be empty. If this set is not empty, it is guaranteed to only
     *         contain instances of <code>String</code>. This set is
     *         guaranteed to be <code>null</code> if
     *         haveEnabledContextIdsChanged() is <code>false</code> and is
     *         guaranteed to not be null if haveEnabledContextIdsChanged() is
     *         <code>true</code>.
     */
    public Set getPreviouslyEnabledContextIds() {
        return previouslyEnabledContextIds;
    }

    /**
     * Returns whether or not the definedContextIds property changed.
     *
     * @return true, iff the definedContextIds property changed.
     */
    public boolean haveDefinedContextIdsChanged() {
        return definedContextIdsChanged;
    }

    /**
     * Returns whether or not the enabledContextIds property changed.
     *
     * @return true, iff the enabledContextIds property changed.
     */
    public boolean haveEnabledContextIdsChanged() {
        return enabledContextIdsChanged;
    }

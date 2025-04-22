        if (definedActivityIdsChanged) {
            this.previouslyDefinedActivityIds = Util.safeCopy(
                    previouslyDefinedActivityIds, String.class);
        } else {
            this.previouslyDefinedActivityIds = null;
        }

        if (definedCategoryIdsChanged) {
            this.previouslyDefinedCategoryIds = Util.safeCopy(
                    previouslyDefinedCategoryIds, String.class);
        } else {
            this.previouslyDefinedCategoryIds = null;
        }

        if (enabledActivityIdsChanged) {
            this.previouslyEnabledActivityIds = Util.safeCopy(
                    previouslyEnabledActivityIds, String.class);
        } else {
            this.previouslyEnabledActivityIds = null;
        }

        this.activityManager = activityManager;
        this.definedActivityIdsChanged = definedActivityIdsChanged;
        this.definedCategoryIdsChanged = definedCategoryIdsChanged;
        this.enabledActivityIdsChanged = enabledActivityIdsChanged;
    }

    /**
     * Returns the instance of the interface that changed.
     *
     * @return the instance of the interface that changed. Guaranteed not to be
     *         <code>null</code>.
     */
    public IActivityManager getActivityManager() {
        return activityManager;
    }

    /**
     * Returns the activity identifiers that were previously defined.
     *
     * @return The set of defined activity identifiers before the changed; may
     *         be empty, but never <code>null</code>. This set will only
     *         contain strings.
     */
    public final Set getPreviouslyDefinedActivityIds() {
        return previouslyDefinedActivityIds;
    }

    /**
     * Returns the category identifiers that were previously defined.
     *
     * @return The set of defined category identifiers before the changed; may
     *         be empty, but never <code>null</code>. This set will only
     *         contain strings.
     */
    public final Set getPreviouslyDefinedCategoryIds() {
        return previouslyDefinedCategoryIds;
    }

    /**
     * Returns the activity identifiers that were previously enabled.
     *
     * @return The set of enabled activity identifiers before the changed; may
     *         be empty, but never <code>null</code>. This set will only
     *         contain strings.
     */
    public final Set getPreviouslyEnabledActivityIds() {
        return previouslyEnabledActivityIds;
    }

    /**
     * Returns whether or not the definedActivityIds property changed.
     *
     * @return <code>true</code>, iff the definedActivityIds property changed.
     */
    public boolean haveDefinedActivityIdsChanged() {
        return definedActivityIdsChanged;
    }

    /**
     * Returns whether or not the definedCategoryIds property changed.
     *
     * @return <code>true</code>, iff the definedCategoryIds property changed.
     */
    public boolean haveDefinedCategoryIdsChanged() {
        return definedCategoryIdsChanged;
    }

    /**
     * Returns whether or not the enabledActivityIds property changed.
     *
     * @return <code>true</code>, iff the enabledActivityIds property changed.
     */
    public boolean haveEnabledActivityIdsChanged() {
        return enabledActivityIdsChanged;
    }

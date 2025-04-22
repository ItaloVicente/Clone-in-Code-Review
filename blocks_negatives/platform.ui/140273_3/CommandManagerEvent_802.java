        if (definedCategoryIdsChanged) {
            this.previouslyDefinedCategoryIds = Util.safeCopy(
                    previouslyDefinedCategoryIds, String.class);
        } else {
            this.previouslyDefinedCategoryIds = null;
        }

        if (definedCommandIdsChanged) {
            this.previouslyDefinedCommandIds = Util.safeCopy(
                    previouslyDefinedCommandIds, String.class);
        } else {
            this.previouslyDefinedCommandIds = null;
        }

        if (definedKeyConfigurationIdsChanged) {
            this.previouslyDefinedKeyConfigurationIds = Util.safeCopy(
                    previouslyDefinedKeyConfigurationIds, String.class);
        } else {
            this.previouslyDefinedKeyConfigurationIds = null;
        }

        this.commandManager = commandManager;
        this.activeContextIdsChanged = activeContextIdsChanged;
        this.activeKeyConfigurationIdChanged = activeKeyConfigurationIdChanged;
        this.activeLocaleChanged = activeLocaleChanged;
        this.activePlatformChanged = activePlatformChanged;
        this.definedCategoryIdsChanged = definedCategoryIdsChanged;
        this.definedCommandIdsChanged = definedCommandIdsChanged;
        this.definedKeyConfigurationIdsChanged = definedKeyConfigurationIdsChanged;
    }

    /**
     * Returns the instance of the interface that changed.
     *
     * @return the instance of the interface that changed. Guaranteed not to be
     *         <code>null</code>.
     */

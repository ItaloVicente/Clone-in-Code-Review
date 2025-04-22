        }

        return null;
    }

    /**
     * Create the editor action bar contributor for editors of this type.
     *
     * @return the action bar contributor, or <code>null</code>
     */
    public IEditorActionBarContributor createActionBarContributor() {
        if (configurationElement == null) {
            return null;
        }

        String className = configurationElement
                .getAttribute(IWorkbenchRegistryConstants.ATT_CONTRIBUTOR_CLASS);
        if (className == null) {

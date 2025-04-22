                super.updateNavigationButtons();
                updateTitle();
            }
        };
        drillDownAdapter.addNavigationActions(getViewSite().getActionBars()
                .getToolBarManager());
    }

    /**
     * Returns whether the preference to link navigator selection to active editor is enabled.
     * @since 2.0
     */
    protected boolean isLinkingEnabled() {
        IPreferenceStore store = getPlugin().getPreferenceStore();
        return store.getBoolean(LINK_NAVIGATOR_TO_EDITOR);
    }

    /**
     * Links to editor (if option enabled)
     * @since 2.0
     */
    protected void linkToEditor(IStructuredSelection selection) {
        if (!isLinkingEnabled()) {

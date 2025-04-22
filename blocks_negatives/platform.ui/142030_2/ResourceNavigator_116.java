        this.linkingEnabled = enabled;

        settings.put(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR,
                enabled);

        if (enabled) {
            IEditorPart editor = getSite().getPage().getActiveEditor();
            if (editor != null) {
                editorActivated(editor);
            }
        }
        openAndLinkWithEditorHelper.setLinkWithEditor(enabled);
    }

    /**
     * Sets the resource sorter.
     *
     * @param sorter the resource sorter
     * @since 2.0
     * @deprecated as of 3.3, use {@link ResourceNavigator#setComparator(ResourceComparator)}
     */
    @Deprecated

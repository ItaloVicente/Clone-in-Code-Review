    /**
     * Adds a view placeholder to this folder.
     * A view placeholder is used to define the position of a view before the view
     * appears.  Initially, it is invisible; however, if the user ever opens a view
     * whose compound id matches the placeholder, the view will appear at the same
     * location as the placeholder.
     * See the {@link IPageLayout} type documentation for more details about compound ids.
     * If the placeholder contains wildcards, it remains in the layout, otherwise
     * it is replaced by the view.
     * If the primary id of the placeholder has no wildcards, it must refer to a view
     * contributed to the workbench's view extension point
     * (named <code>"org.eclipse.ui.views"</code>).
     *
     * @param viewId the compound view id (wildcards allowed)
     */
    void addPlaceholder(String viewId);

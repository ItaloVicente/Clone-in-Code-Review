    /**
     * Adds a view with the given compound id to this folder.
     * See the {@link IPageLayout} type documentation for more details about compound ids.
     * The primary id must name a view contributed to the workbench's view extension point
     * (named <code>"org.eclipse.ui.views"</code>).
     *
     * @param viewId the view id
     */
    public void addView(String viewId);

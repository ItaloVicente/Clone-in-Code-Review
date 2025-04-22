    /**
     * Adds a view placeholder to this page layout.
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
     * @param relationship the position relative to the reference part;
     *  one of <code>TOP</code>, <code>BOTTOM</code>, <code>LEFT</code>,
     *  or <code>RIGHT</code>
     * @param ratio a ratio specifying how to divide the space currently occupied by the reference part,
     *    in the range <code>0.05f</code> to <code>0.95f</code>.
     *    Values outside this range will be clipped to facilitate direct manipulation.
     *    For a vertical split, the part on top gets the specified ratio of the current space
     *    and the part on bottom gets the rest.
     *    Likewise, for a horizontal split, the part at left gets the specified ratio of the current space
     *    and the part at right gets the rest.
     * @param refId the id of the reference part; either a view id, a folder id,
     *   or the special editor area id returned by <code>getEditorArea</code>
     */
	void addPlaceholder(String viewId, int relationship, float ratio,
            String refId);

    /**
     * Adds an item to the Show In prompter.
     * The id must name a view contributed to the workbench's view extension point
     * (named <code>"org.eclipse.ui.views"</code>).
     *
     * @param id the view id
     *
     * @since 2.1
     */

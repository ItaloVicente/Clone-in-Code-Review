    /**
     * Adds a view with the given compound id to this page layout.
     * See the {@link IPageLayout} type documentation for more details about compound ids.
     * The primary id must name a view contributed to the workbench's view extension point
     * (named <code>"org.eclipse.ui.views"</code>).
     *
     * @param viewId the compound view id
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
	void addView(String viewId, int relationship, float ratio,
            String refId);

    /**
     * Creates and adds a new folder with the given id to this page layout.
     * The position and relative size of the folder is expressed relative to
     * a reference part.
     *
     * @param folderId the id for the new folder.  This must be unique within
     *  the layout to avoid collision with other parts.
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
     * @return the new folder
     */
	IFolderLayout createFolder(String folderId, int relationship,
            float ratio, String refId);

    /**
     * Creates and adds a placeholder for a new folder with the given id to this page layout.
     * The position and relative size of the folder is expressed relative to
     * a reference part.
     *
     * @param folderId the id for the new folder.  This must be unique within
     *  the layout to avoid collision with other parts.
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
     * @return a placeholder for the new folder
     * @since 2.0
     */
	IPlaceholderFolderLayout createPlaceholderFolder(String folderId,
            int relationship, float ratio, String refId);

    /**
     * Returns the special identifier for the editor area in this page
     * layout.  The identifier for the editor area is also stored in
     * <code>ID_EDITOR_AREA</code>.
     * <p>
     * The editor area is automatically added to each layout before anything else.
     * It should be used as the point of reference when adding views to a layout.
     * </p>
     *
     * @return the special id of the editor area
     */

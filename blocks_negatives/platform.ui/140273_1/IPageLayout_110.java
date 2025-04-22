	 * @param viewId
	 *            the compound view id (wildcards allowed)
	 * @param relationship
	 *            the position relative to the reference part; one of
	 *            <code>TOP</code>, <code>BOTTOM</code>, <code>LEFT</code>,
	 *            or <code>RIGHT</code>
	 * @param ratio
	 *            a ratio specifying how to divide the space currently occupied
	 *            by the reference part, in the range <code>0.05f</code> to
	 *            <code>0.95f</code>. Values outside this range will be
	 *            clipped to facilitate direct manipulation. For a vertical
	 *            split, the part on top gets the specified ratio of the current
	 *            space and the part on bottom gets the rest. Likewise, for a
	 *            horizontal split, the part at left gets the specified ratio of
	 *            the current space and the part at right gets the rest.
	 * @param refId
	 *            the id of the reference part; either a view id, a folder id,
	 *            or the special editor area id returned by
	 *            <code>getEditorArea</code>
	 * @param showTitle
	 *            true to show the view's title, false if not

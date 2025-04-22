	 * @param element
	 *            The configuration element from which the actions and menus
	 *            should be read; must not be <code>null</code>, but may be
	 *            empty.
	 * @param id
	 *            The identifier of the contribution being made. This could be
	 *            an action set, an editor contribution, a view contribution, a
	 *            viewer contribution or an object contribution. This value must
	 *            not be <code>null</code>.
	 * @param warningsToLog
	 *            The list of warnings already logged for this extension point;
	 *            must not be <code>null</code>.
	 * @param visibleWhenExpression
	 *            The expression controlling visibility of the corresponding
	 *            menu elements; may be <code>null</code>.
	 * @param viewId
	 *            The view to which this handler is associated. This value is
	 *            required if this is a view action; otherwise it can be
	 *            <code>null</code>.
	 * @return An array of references to the created menu elements. This value
	 *         may be <code>null</code> if there was a problem parsing the
	 *         configuration element.

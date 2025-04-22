	 * @param primaryId
	 *            The identifier of the primary object to which this action
	 *            belongs. This is used to auto-generate command identifiers
	 *            when required. The <code>primaryId</code> must not be
	 *            <code>null</code>.
	 * @param elements
	 *            The action elements to be read; must not be <code>null</code>.
	 * @param warningsToLog
	 *            The collection of warnings while parsing this extension point;
	 *            must not be <code>null</code>.
	 * @param visibleWhenExpression
	 *            The expression controlling visibility of the corresponding
	 *            menu elements; may be <code>null</code>.
	 * @param viewId
	 *            The view to which this handler is associated. This value is
	 *            required if this is a view action; otherwise it can be
	 *            <code>null</code>.

	 * @param element
	 *            The action element from which the handler should be read; must
	 *            not be <code>null</code>.
	 * @param actionId
	 *            The identifier of the action for which a handler is being
	 *            created; must not be <code>null</code>.
	 * @param command
	 *            The command for which this handler applies; must not be
	 *            <code>null</code>.
	 * @param activeWhenExpression
	 *            The expression controlling when the handler is active; may be
	 *            <code>null</code>.
	 * @param viewId
	 *            The view to which this handler is associated. This value is
	 *            required if this is a view action; otherwise it can be
	 *            <code>null</code>.
	 * @param warningsToLog
	 *            The collection of warnings while parsing this extension point;
	 *            must not be <code>null</code>.

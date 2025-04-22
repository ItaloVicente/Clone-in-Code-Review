	 * @param element
	 *            The configuration element from which the real class can be
	 *            loaded at run-time; must not be <code>null</code>.
	 * @param delegateAttributeName
	 *            The name of the attibute or element containing the action
	 *            delegate; must not be <code>null</code>.
	 * @param actionId
	 *            The identifier of the underlying action; may be
	 *            <code>null</code>.
	 * @param command
	 *            The command with which the action delegate will be associated;
	 *            must not be <code>null</code>.
	 * @param window
	 *            The workbench window in which this delegate will be active;
	 *            must not be <code>null</code>.
	 * @param style
	 *            The image style with which the icons are associated; may be
	 *            <code>null</code>.
	 * @param enabledWhenExpression
	 *            The name of the element containing the enabledWhen expression.
	 *            This should be a child of the
	 *            <code>configurationElement</code>. If this value is
	 *            <code>null</code>, then there is no enablement expression
	 *            (i.e., enablement will be delegated to the handler when
	 *            possible).
	 * @param viewId
	 *            The identifier of the view to which this proxy is bound; may
	 *            be <code>null</code> if this proxy is not for an
	 *            {@link IViewActionDelegate}.

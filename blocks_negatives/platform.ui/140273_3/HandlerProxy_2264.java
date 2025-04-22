	 * @param commandId the id for this handler
	 * @param configurationElement
	 *            The configuration element from which the real class can be
	 *            loaded at run-time; must not be <code>null</code>.
	 * @param handlerAttributeName
	 *            The name of the attribute or element containing the handler
	 *            executable extension; must not be <code>null</code>.
	 * @param enabledWhenExpression
	 *            The name of the element containing the enabledWhen expression.
	 *            This should be a child of the
	 *            <code>configurationElement</code>. If this value is
	 *            <code>null</code>, then there is no enablement expression
	 *            (i.e., enablement will be delegated to the handler when
	 *            possible).
	 * @param evaluationService
	 *            The evaluation service to manage enabledWhen expressions
	 *            trying to evaluate the <code>enabledWhenExpression</code>.
	 *            This value may be <code>null</code> only if the
	 *            <code>enabledWhenExpression</code> is <code>null</code>.

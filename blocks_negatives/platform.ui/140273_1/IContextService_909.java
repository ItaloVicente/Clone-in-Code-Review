	 * @param contextId
	 *            The identifier for the context which should be activated; must
	 *            not be <code>null</code>.
	 * @param expression
	 *            This expression must evaluate to <code>true</code> before
	 *            this context will really become active. The expression may be
	 *            <code>null</code> if the context should always be active.
	 * @param global
	 *            Indicates that the handler should be activated irrespectively
	 *            of whether the corresponding workbench component (e.g.,
	 *            window, part, etc.) is active.

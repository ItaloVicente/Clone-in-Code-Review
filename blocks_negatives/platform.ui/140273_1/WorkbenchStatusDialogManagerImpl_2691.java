	 * @param decorator
	 *            - the decorator to be set. Only
	 *            {@link ILabelDecorator#decorateText(String, Object)} method
	 *            will be used. This method should return <code>null</code> if
	 *            and only if the first argument is null. StatusAdapter is
	 *            passed as second parameter. Other methods should have default
	 *            behavior as they may be used in future versions of the dialog.

	 * @param command
	 *            The parameterized command that is already specialized. Must
	 *            not be <code>null</code>.
	 * @param element
	 *            The callback to register for this specialized command
	 *            instance. Must not be <code>null</code>.
	 * @return A reference for the registered element that can be used to
	 *         unregister it.
	 * @throws NotDefinedException
	 *             If the command included in the ParameterizedCommand is not
	 *             defined, or the element is <code>null</code>.

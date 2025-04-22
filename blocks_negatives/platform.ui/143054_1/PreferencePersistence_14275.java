	 * @param memento
	 *            The memento from which the parameters should be read; must not
	 *            be <code>null</code>.
	 * @param warningsToLog
	 *            The list of warnings found during parsing. Warnings found will
	 *            parsing the parameters will be appended to this list. This
	 *            value must not be <code>null</code>.
	 * @param command
	 *            The command around which the parameterization should be
	 *            created; must not be <code>null</code>.
	 * @return The array of parameters found for this memento; <code>null</code>
	 *         if none can be found.

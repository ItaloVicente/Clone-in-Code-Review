	/**
	 * Returns a collection of all {@link MInputPart} with the inputURI-Attribute
	 * set to the given value
	 *
	 * @param inputUri
	 *            the input uri to search for, must not be <code>null</code>
	 * @return list of parts or an empty collection
	 * @throws AssertionFailedException
	 *             if null passed as argument
	 * @deprecated This method should never be used as MInputPart are deprecated
	 * @noreference This method is not intended to be referenced by clients.
	 *      509868</a>
	 */
	@Deprecated Collection<MInputPart> getInputParts(String inputUri);


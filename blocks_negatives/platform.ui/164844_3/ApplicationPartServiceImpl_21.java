	/**
	 * @noreference This method is not intended to be referenced by clients.
	 *      509868</a>
	 */
	@Deprecated
	@Override
	public Collection<MInputPart> getInputParts(String inputUri) {
		return getActiveWindowService().getInputParts(inputUri);
	}


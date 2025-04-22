	/**
	 * @noreference This method is not intended to be referenced by clients.
	 *      509868</a>
	 */
	@Deprecated
	@Override
	public Collection<MInputPart> getInputParts(String inputUri) {
		Assert.isNotNull(inputUri, "Input uri must not be null"); //$NON-NLS-1$

		Collection<MInputPart> rv = new ArrayList<>();

		for (MInputPart p : getParts(MInputPart.class, null)) {
			if (inputUri.equals(p.getInputURI())) {
				rv.add(p);
			}
		}

		return rv;
	}


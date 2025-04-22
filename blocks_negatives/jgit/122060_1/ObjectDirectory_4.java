	/**
	 * {@inheritDoc}
	 * <p>
	 * Compute the location of a loose object file.
	 */
	@Override
	public File fileFor(AnyObjectId objectId) {
		String n = objectId.name();
		String d = n.substring(0, 2);
		String f = n.substring(2);
		return new File(new File(getDirectory(), d), f);
	}


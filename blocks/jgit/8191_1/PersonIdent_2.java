	public PersonIdent(final String aName
			final long aWhen
		if (aName == null)
			throw new IllegalArgumentException(
					"Name of PersonIdent must not be null.");
		if (aEmailAddress == null)
			throw new IllegalArgumentException(
					"E-mail address of PersonIdent must not be null.");
		name = aName;
		emailAddress = aEmailAddress;

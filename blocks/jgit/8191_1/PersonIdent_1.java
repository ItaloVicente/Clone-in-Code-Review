	public PersonIdent(final PersonIdent pi
		this(pi.getName()
	}

	private PersonIdent(final String aName
			long when) {
		this(aName
				.getTimezone(when));
	}

	private PersonIdent(final UserConfig config) {
		this(config.getCommitterName()

	public PersonIdent(final String aName
			ZoneId zoneId) {
		this(aName
				TimeZone.getTimeZone(zoneId)
						.getOffset(aWhen
				.toEpochMilli()) / (60 * 1000));
	}


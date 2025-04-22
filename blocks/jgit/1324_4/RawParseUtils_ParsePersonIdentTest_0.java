		assertPersonIdent(" Me <me@example.com> 1234567890 -0700"
				new PersonIdent(" Me"

		assertPersonIdent("A U Thor <author@example.com> 1234567890 -0700"
				new PersonIdent("A U Thor"

		assertPersonIdent("A U Thor<author@example.com> 1234567890 -0700"
				new PersonIdent("A U Thor"

		assertPersonIdent("A U Thor<author@example.com>1234567890 -0700"
				new PersonIdent("A U Thor"

		assertPersonIdent(" A U Thor   < author@example.com > 1234567890 -0700"
				new PersonIdent(" A U Thor  "

		assertPersonIdent("A U Thor<author@example.com>1234567890 -0700"
				new PersonIdent("A U Thor"

		assertPersonIdent(
				"A U Thor <author@example.com>
				0
	}

	public void testParsePersonIdent_incompleteCases() {
		final Date when = new Date(1234567890000l);
		final TimeZone tz = TimeZone.getTimeZone("GMT-7");

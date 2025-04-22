	@Test
	public void never() throws ParseException {
		GregorianCalendar cal = new GregorianCalendar(SystemReader
				.getInstance().getTimeZone()
				.getLocale());
		Date parse = GitDateParser.parse("never"
		Assert.assertEquals(GitDateParser.NEVER
		parse = GitDateParser.parse("never"
		Assert.assertEquals(GitDateParser.NEVER
	}


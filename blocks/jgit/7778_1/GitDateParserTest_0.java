	@Test
	public void never() throws ParseException {
		GregorianCalendar cal = new GregorianCalendar(SystemReader
				.getInstance().getTimeZone()
				.getLocale());
		Date parse = GitDateParser.parse("never"
		Assert.assertNull(parse);
		parse = GitDateParser.parse("never"
		Assert.assertNull(parse);
	}


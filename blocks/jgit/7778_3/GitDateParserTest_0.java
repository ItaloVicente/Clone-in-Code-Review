	@Test
	public void never() throws ParseException {
		GregorianCalendar cal = new GregorianCalendar(SystemReader
				.getInstance().getTimeZone()
				.getLocale());
		Date parse = GitDateParser.parse("never"
		Assert.assertNull(parse);
		Assert.assertTrue(GitDateParser.isNever("never"));
		parse = GitDateParser.parse("never"
		Assert.assertNull(parse);
	}


	public void badlyFormatted() {
		Calendar ref = new GregorianCalendar(SystemReader.getInstance()
				.getTimeZone(), SystemReader.getInstance().getLocale());
		Assert.assertNull(GitDateParser.parse("foo", ref));
		Assert.assertNull(GitDateParser.parse("", ref));
		Assert.assertNull(GitDateParser.parse("", null));
		Assert.assertNull(GitDateParser.parse("1970", ref));
		Assert.assertNull(GitDateParser.parse("3000.3000.3000", ref));
		Assert.assertNull(GitDateParser.parse("3 yesterday ago", ref));
		Assert.assertNull(GitDateParser.parse("now yesterday ago", ref));
		Assert.assertNull(GitDateParser.parse("yesterdays", ref));
		Assert.assertNull(GitDateParser.parse("3.day. 2.week.ago", ref));
		Assert.assertNull(GitDateParser.parse("day ago", ref));
		Assert.assertNull(GitDateParser.parse("Gra Feb 21 15:35:00 2007 +0100",
				null));
		Assert.assertNull(GitDateParser.parse("Sun Feb 21 15:35:00 2007 +0100",
				null));
		Assert.assertNull(GitDateParser.parse(
				"Wed Feb 21 15:35:00 Grand +0100",
				null));
	}

	@Test
	public void yesterday() {

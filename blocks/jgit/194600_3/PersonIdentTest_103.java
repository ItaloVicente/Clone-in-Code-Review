	@Test
	public void testNewIdentInstant() {
		PersonIdent p = new PersonIdent("A U Thor"
				Instant.ofEpochMilli(1142878501000L)
				ZoneId.of("America/New_York"));
		assertEquals("A U Thor"
		assertEquals("author@example.com"
		assertEquals(Instant.ofEpochMilli(1142878501000L)
				p.getWhenAsInstant());
		assertEquals("A U Thor <author@example.com> 1142878501 -0500"
				p.toExternalString());
		assertEquals(ZoneId.of("GMT-05:00")
	}

	@Test
	public void testNewIdentInstant2() {
		final PersonIdent p = new PersonIdent("A U Thor"
				Instant.ofEpochMilli(1142878501000L)
				ZoneId.of("Asia/Kolkata"));
		assertEquals("A U Thor"
		assertEquals("author@example.com"
		assertEquals(Instant.ofEpochMilli(1142878501000L)
				p.getWhenAsInstant());
		assertEquals("A U Thor <author@example.com> 1142878501 +0530"
				p.toExternalString());
		assertEquals(ZoneId.of("GMT+05:30")
	}


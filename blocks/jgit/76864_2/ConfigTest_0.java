
	@Test
	public void testTimeUnit() throws ConfigInvalidException {
		assertEquals(0
		assertEquals(2
		assertEquals(200

		assertEquals(0
		assertEquals(2
		assertEquals(231
		assertEquals(1
		assertEquals(300

		assertEquals(2
		assertEquals(2
		assertEquals(1
		assertEquals(10

		assertEquals(5
		assertEquals(5
		assertEquals(1
		assertEquals(48

		assertEquals(5
		assertEquals(5
		assertEquals(1
		assertEquals(48
		assertEquals(48

		assertEquals(4
		assertEquals(1
		assertEquals(14

		assertEquals(7
		assertEquals(7
		assertEquals(14
		assertEquals(14

		assertEquals(30
		assertEquals(30
		assertEquals(60
		assertEquals(60

		assertEquals(365
		assertEquals(365
		assertEquals(365 * 2
	}

	private long parseTime(String value
			throws ConfigInvalidException {
		Config c = parse("[a]\na=" + value + "\n");
		return c.getTimeUnit("a"
	}

	@Test
	public void testTimeUnitDefaultValue() throws ConfigInvalidException {
		assertEquals(20
				MILLISECONDS));
		assertEquals(20
				MILLISECONDS));

		assertEquals(20
				MILLISECONDS));
	}

	@Test
	public void testTimeUnitInvalid() throws ConfigInvalidException {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx
				.expectMessage("Invalid time unit value: a.a=1 monttthhh");
		parseTime("1 monttthhh"
	}

	@Test
	public void testTimeUnitInvalidWithSection() throws ConfigInvalidException {
		Config c = parse("[a \"b\"]\na=1 monttthhh\n");
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Invalid time unit value: a.b.a=1 monttthhh");
		c.getTimeUnit("a"
	}

	@Test
	public void testTimeUnitNegative() throws ConfigInvalidException {
		expectedEx.expect(IllegalArgumentException.class);
		parseTime("-1"
	}

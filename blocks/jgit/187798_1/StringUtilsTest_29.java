
	@Test
	public void testFormatWithSuffix() {
		assertEquals("1023"
		assertEquals("1k"
		assertEquals("1025"
		assertEquals("1048575"
		assertEquals("1m"
		assertEquals("1048577"
		assertEquals("1073741823"
				StringUtils.formatWithSuffix(1024 * 1024 * 1024 - 1));
		assertEquals("1g"
		assertEquals("1073741825"
				StringUtils.formatWithSuffix(1024 * 1024 * 1024 + 1));
		assertEquals("3k"
		assertEquals("3m"
		assertEquals("2050k"
				StringUtils.formatWithSuffix(2 * 1024 * 1024 + 2048));
		assertEquals("3g"
				StringUtils.formatWithSuffix(3L * 1024 * 1024 * 1024));
		assertEquals("3000"
		assertEquals("3000000"
		assertEquals("1953125k"
		assertEquals("2000000010"
		assertEquals("3000000000"
				StringUtils.formatWithSuffix(3_000_000_000L));
	}

	@Test
	public void testParseWithSuffix() {
		assertEquals(1024
		assertEquals(1024
		assertEquals(1024
		assertEquals(1024
		assertEquals(1024
		assertEquals(1024
		assertEquals(1024 * 1024
		assertEquals(1024 * 1024
		assertEquals(-1024 * 1024
				StringUtils.parseIntWithSuffix("-1M"
		assertEquals(1_000_000
				StringUtils.parseIntWithSuffix("  1000000\r\n"
		assertEquals(1024 * 1024 * 1024
				StringUtils.parseIntWithSuffix("1g"
		assertEquals(1024 * 1024 * 1024
				StringUtils.parseIntWithSuffix("1G"
		assertEquals(3L * 1024 * 1024 * 1024
				StringUtils.parseLongWithSuffix("3g"
		assertEquals(3L * 1024 * 1024 * 1024
				StringUtils.parseLongWithSuffix("3G"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseIntWithSuffix("2G"
		assertEquals(2L * 1024 * 1024 * 1024
				StringUtils.parseLongWithSuffix("2G"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseLongWithSuffix("-1m"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseLongWithSuffix("-1000"
		assertThrows(StringIndexOutOfBoundsException.class
				() -> StringUtils.parseLongWithSuffix(""
		assertThrows(StringIndexOutOfBoundsException.class
				() -> StringUtils.parseLongWithSuffix("   \t   \n"
		assertThrows(StringIndexOutOfBoundsException.class
				() -> StringUtils.parseLongWithSuffix("k"
		assertThrows(StringIndexOutOfBoundsException.class
				() -> StringUtils.parseLongWithSuffix("m"
		assertThrows(StringIndexOutOfBoundsException.class
				() -> StringUtils.parseLongWithSuffix("g"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseLongWithSuffix("1T"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseLongWithSuffix("1t"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseLongWithSuffix("Nonumber"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseLongWithSuffix("0x001f"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseLongWithSuffix("beef"
		assertThrows(NumberFormatException.class
				() -> StringUtils.parseLongWithSuffix("8000000000000000000G"
						false));
	}

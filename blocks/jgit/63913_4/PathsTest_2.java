
	@Test
	public void testPathCompare() {
		byte[] a = Constants.encode("afoo/bar.c");
		byte[] b = Constants.encode("bfoo/bar.c");

		assertEquals(0
		assertEquals(-1
		assertEquals(1

		a = Constants.encode("a");
		b = Constants.encode("aa");
		assertEquals(-97
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(-50
		assertEquals(97

		a = Constants.encode("a");
		b = Constants.encode("a");
		assertEquals(0
				a
				b
		assertEquals(0
				a
				b
		assertEquals(-47
				a
				b
		assertEquals(47
				a
				b

		assertEquals(0
				a
				b
		assertEquals(0
				a
				b

		a = Constants.encode("a.c");
		b = Constants.encode("a");
		byte[] c = Constants.encode("a0c");
		assertEquals(-1
				a
				b
		assertEquals(-1
				b
				c
	}

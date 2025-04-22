	@Test
	public void testRejectDevicesOnWindows() {
		checker.setSafeForWindows(true);

		String[] bad = { "CON"
				"COM4"
				"LPT3"
		for (String b : bad) {
			try {
				checkOneName(b);
				fail("incorrectly accepted " + b);
			} catch (CorruptObjectException e) {
				assertEquals("invalid name '" + b + "'"
			}
			try {
				checkOneName(b + ".txt");
				fail("incorrectly accepted " + b + ".txt");
			} catch (CorruptObjectException e) {
				assertEquals("invalid name '" + b + "'"
			}
		}
	}


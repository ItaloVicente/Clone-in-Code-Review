		byte[] data = Constants.encode(b.toString());
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals(
					"invalid name '\u206B.git' contains ignorable Unicode characters",
					e.getMessage());
		}

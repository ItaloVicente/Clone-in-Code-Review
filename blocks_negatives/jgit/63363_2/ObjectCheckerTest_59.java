		data = concat(data, Constants.encode(b.toString()));
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals(
					"invalid name contains byte sequence '0xef' which is not a valid UTF-8 character",
					e.getMessage());
		}

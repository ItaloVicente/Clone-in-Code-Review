		try {
			checker.setSafeForWindows(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("duplicate entry names", e.getMessage());
		}

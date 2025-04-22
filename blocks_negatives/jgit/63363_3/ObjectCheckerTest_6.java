
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkCommit(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no tree header", e.getMessage());
		}

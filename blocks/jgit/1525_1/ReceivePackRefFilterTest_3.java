		try {
			receive(rp
			fail("Expected UnpackException");
		} catch (UnpackException failed) {
			Throwable err = failed.getCause();
			assertTrue(err instanceof MissingObjectException);
			MissingObjectException moe = (MissingObjectException) err;
			assertEquals(b
		}

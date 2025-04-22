	public void testIncorrectTypeInCAS() throws Throwable {
		client.set("x", 0, "not a long");
		try {
			Long rv=mutator.cas("x", 1L, 0, mutation);
			fail("Expected RuntimeException on invalid type mutation, got "
				+ rv);
		} catch(RuntimeException e) {
			assertEquals("Couldn't get a CAS in 50 attempts", e.getMessage());
		}
	}

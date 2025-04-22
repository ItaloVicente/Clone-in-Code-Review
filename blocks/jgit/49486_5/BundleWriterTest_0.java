	@Test
	public void testAbortWrite() throws Exception {
		boolean caught = false;
		try {
			makeBundleWithCallback(
					"refs/heads/aa"
		} catch (WriteAbortedException e) {
			caught = true;
		}
		assertTrue(caught);
	}


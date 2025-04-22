		assertTrue("no references"
	}

	@Test
	public void testGetAdditionalRefs() throws IOException {
		update("refs/heads/master"

		List<Ref> addl = refdb.getAdditionalRefs();
		assertEquals(1
		assertEquals("refs/txn/committed"
		assertEquals(getTxnCommitted()

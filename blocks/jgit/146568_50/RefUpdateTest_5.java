	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testHEADisSymbolic() throws  Exception {
		Ref h = db.exactRef("HEAD");
		assertTrue(h.isSymbolic());
		assertEquals(h.getTarget().getName()
	}

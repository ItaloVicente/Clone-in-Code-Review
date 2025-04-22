	@Test
	public void testAddM1() throws Exception {
		ApplyResult result = init("M1"
		assertEquals(1
		assertTrue(result.getUpdatedFiles().get(0).canExecute());
	}

	@Test
	public void testModifyM2() throws Exception {
		ApplyResult result = init("M2"
		assertEquals(1
		assertTrue(result.getUpdatedFiles().get(0).canExecute());
		assertEquals(new File(db.getWorkTree()
				.get(0));
	}

	@Test
	public void testModifyM3() throws Exception {
		ApplyResult result = init("M3"
		assertEquals(1
		assertFalse(result.getUpdatedFiles().get(0).canExecute());
		assertEquals(new File(db.getWorkTree()
				.get(0));
	}


	@Test
	public void testNonASCII() throws Exception {
		ApplyResult result = init("NonASCII");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCII2() throws Exception {
		ApplyResult result = init("NonASCII2");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCIIAdd() throws Exception {
		ApplyResult result = init("NonASCIIAdd");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCIIAdd2() throws Exception {
		ApplyResult result = init("NonASCIIAdd2"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCIIDel() throws Exception {
		ApplyResult result = init("NonASCIIDel"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		assertFalse(new File(db.getWorkTree()
	}


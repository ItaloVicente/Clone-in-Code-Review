	@Test
	public void testShiftUp() throws Exception {
		ApplyResult result = init("ShiftUp");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testShiftUp2() throws Exception {
		ApplyResult result = init("ShiftUp2");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testShiftDown() throws Exception {
		ApplyResult result = init("ShiftDown");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testShiftDown2() throws Exception {
		ApplyResult result = init("ShiftDown2");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}


	@Test
	public void testRenameNoHunks() throws Exception {
		ApplyResult result = init("RenameNoHunks"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}


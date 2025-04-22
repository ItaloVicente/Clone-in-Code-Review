		assertEquals(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
	}

	@Test
	public void testAddBinary() throws Exception {
		ApplyResult result = init("Binary"
		assertEquals(1

		byte[] output = Files.readAllBytes(new File(db.getWorkTree()
		byte[] ref = readFile("Binary_PostImage");

		JGitTestUtil.assertEquals(output

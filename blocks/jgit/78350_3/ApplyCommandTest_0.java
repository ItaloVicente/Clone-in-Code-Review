	@Test
	public void testAddBinary() throws Exception {
		ApplyResult result = init("Binary"
		assertEquals(1

		File createdFile = new File(db.getWorkTree()
		byte[] out = Files.readAllBytes(createdFile.toPath());
		byte[] ref = readFile("Binary_PostImage");

		JGitTestUtil.assertEquals(out
	}


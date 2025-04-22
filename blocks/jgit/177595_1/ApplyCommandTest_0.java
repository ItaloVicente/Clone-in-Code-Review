	private void checkBinary(String name
			throws Exception {
		try (Git git = new Git(db)) {
			byte[] post = IO
					.readWholeStream(getTestResource(name + "_PostImage")
					.array();
			File f = new File(db.getWorkTree()
			if (hasPreImage) {
				byte[] pre = IO
						.readWholeStream(getTestResource(name + "_PreImage")
						.array();
				Files.write(f.toPath()
				git.add().addFilepattern(name).call();
				git.commit().setMessage("PreImage").call();
			}
			ApplyResult result = git.apply()
					.setPatch(getTestResource(name + ".patch")).call();
			assertEquals(1
			assertEquals(f
			assertArrayEquals(post
		}
	}

	@Test
	public void testBinaryDelta() throws Exception {
		checkBinary("delta"
	}

	@Test
	public void testBinaryLiteral() throws Exception {
		checkBinary("literal"
	}

	@Test
	public void testBinaryLiteralAdd() throws Exception {
		checkBinary("literal_add"
	}


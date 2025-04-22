	@Repeat(n = 10000
	@Test
	public void detectFileModified() throws IOException {
		File f = createFile("test").toFile();
		write(f
		FileSnapshot snapshot = FileSnapshot.save(f);
		assertEquals("a"
		write(f
		assertTrue(snapshot.isModified(f));
		assertEquals("b"
	}


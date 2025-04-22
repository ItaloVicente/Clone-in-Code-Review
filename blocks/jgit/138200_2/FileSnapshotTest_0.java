	@Test
	public void tesIsModifiedBySameLastModifiedAndDifferentSize() throws Exception {
		File f1 = createFile("foo"
		File f2 = createFile("bar"

		long now = System.currentTimeMillis();
		f1.setLastModified(now);
		f2.setLastModified(now);
		FileSnapshot save = FileSnapshot.save(f1);

		Thread.sleep(3000L);

		assertEquals(save.lastModified()
		assertTrue(save.isModified(f2));
	}


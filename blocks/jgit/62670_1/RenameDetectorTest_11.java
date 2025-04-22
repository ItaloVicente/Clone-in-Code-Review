	@Test
	public void testExactRename_UnstagedFile() throws Exception {
		ObjectId aId = blob("foo");
		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_B

		rd.addAll(Arrays.asList(a
		List<DiffEntry> entries = rd.compute();

		assertEquals(1
		assertRename(a
	}


	@Test
	public void testNoRenames_UntrackedFile() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = ObjectId
				.fromString("3049eb6eee7e1318f4e78e799bf33f1e54af9cbf");

		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_B

		rd.addAll(Arrays.asList(a
		List<DiffEntry> entries = rd.compute();

		assertEquals(2
		assertSame(a
		assertSame(b
	}


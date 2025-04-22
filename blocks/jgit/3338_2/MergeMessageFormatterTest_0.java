	@Test
	public void testIntoHeadOtherThanMaster() throws IOException {
		Ref a = db.getRef("refs/heads/a");
		Ref b = db.getRef("refs/heads/b");
		SymbolicRef head = new SymbolicRef("HEAD"
		String message = formatter.format(Arrays.asList(a)
		assertEquals("Merge branch 'a' into b"
	}


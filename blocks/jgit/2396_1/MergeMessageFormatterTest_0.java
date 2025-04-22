
	@Test
	public void testIntoSymbolicRefHeadPointingToMaster() throws IOException {
		Ref a = db.getRef("refs/heads/a");
		Ref master = db.getRef("refs/heads/master");
		SymbolicRef head = new SymbolicRef("HEAD"
		String message = formatter.format(Arrays.asList(a)
		assertEquals("Merge branch 'a'"
	}

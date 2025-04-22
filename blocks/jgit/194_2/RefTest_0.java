	public void testResolvedSymRef() throws IOException {
		Ref ref = db.getRef(Constants.HEAD);
		assertEquals(Constants.HEAD
		assertTrue("is symbolic ref"
		assertSame(Ref.Storage.LOOSE

		Ref dst = ((SymbolicRef) ref).getTarget();
		assertNotNull("has target"
		assertEquals("refs/heads/master"

		assertSame(dst.getObjectId()
		assertSame(dst.getPeeledObjectId()
		assertEquals(dst.isPeeled()

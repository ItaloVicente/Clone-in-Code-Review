	@Test
	public void testExactRef_FetchHead() throws IOException {
		write(new File(diskRepo.getDirectory()
				+ "\tnot-for-merge"

		Ref r = refdir.exactRef("FETCH_HEAD");
		assertFalse(r.isSymbolic());
		assertEquals(A
		assertEquals("FETCH_HEAD"
		assertFalse(r.isPeeled());
		assertNull(r.getPeeledObjectId());
	}


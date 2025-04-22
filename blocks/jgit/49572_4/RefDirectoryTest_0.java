	@Test
	public void testGetAdditionalRefs_OrigHead() throws IOException {
		writeLooseRef("ORIG_HEAD"

		List<Ref> refs = refdir.getAdditionalRefs();
		assertEquals(1

		Ref r = refs.get(0);
		assertFalse(r.isSymbolic());
		assertEquals(A
		assertEquals("ORIG_HEAD"
		assertFalse(r.isPeeled());
		assertNull(r.getPeeledObjectId());
	}

	@Test
	public void testGetAdditionalRefs_OrigHeadBranch() throws IOException {
		writeLooseRef("refs/heads/ORIG_HEAD"
		List<Ref> refs = refdir.getAdditionalRefs();
		assertArrayEquals(new Ref[0]
	}


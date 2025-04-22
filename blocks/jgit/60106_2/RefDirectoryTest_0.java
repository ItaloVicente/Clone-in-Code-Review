	@Test
	public void testExactRef_EmptyDatabase() throws IOException {
		Ref r;

		r = refdir.exactRef(HEAD);
		assertTrue(r.isSymbolic());
		assertSame(LOOSE
		assertEquals("refs/heads/master"
		assertSame(NEW
		assertNull(r.getTarget().getObjectId());

		assertNull(refdir.exactRef("refs/heads/master"));
		assertNull(refdir.exactRef("refs/tags/v1.0"));
		assertNull(refdir.exactRef("FETCH_HEAD"));
		assertNull(refdir.exactRef("NOT.A.REF.NAME"));
		assertNull(refdir.exactRef("master"));
		assertNull(refdir.exactRef("v1.0"));
	}


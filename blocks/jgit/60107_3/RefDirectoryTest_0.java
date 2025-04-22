	@Test
	public void testGetRef_CycleInSymbolicRef() throws IOException {
		Ref r;

		writeLooseRef("refs/1"
		writeLooseRef("refs/2"
		writeLooseRef("refs/3"
		writeLooseRef("refs/4"
		writeLooseRef("refs/5"
		writeLooseRef("refs/end"

		r = refdir.getRef("1");
		assertEquals("refs/1"
		assertEquals(A
		assertTrue(r.isSymbolic());

		writeLooseRef("refs/5"
		writeLooseRef("refs/6"

		r = refdir.getRef("1");
		assertNull("missing 1 due to cycle"

		writeLooseRef("refs/heads/1"

		r = refdir.getRef("1");
		assertEquals("refs/heads/1"
		assertEquals(B
		assertFalse(r.isSymbolic());
	}


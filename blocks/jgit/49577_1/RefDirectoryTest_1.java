	@Test
	public void testFirstExactRef_Mixed() throws IOException {
		writeLooseRef("refs/heads/A"
		writePackedRef("refs/tags/v1.0"

		Ref a = refdir.firstExactRef("refs/heads/A"
		Ref one = refdir.firstExactRef("refs/tags/v1.0"

		assertEquals("refs/heads/A"
		assertEquals("refs/tags/v1.0"

		assertEquals(A
		assertEquals(v1_0
	}


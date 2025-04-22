	@Test
	public void scanDuplicates() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/banana"
		List<Ref> delta2 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/apple"

		MergedReftable mr = merge(write(delta1
		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/apple"
			assertEquals(id(3)
			assertTrue(rc.next());
			assertEquals("refs/heads/banana"
			assertEquals(id(2)
			assertFalse(rc.next());
		}
	}


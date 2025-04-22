	@Test
	public void scanDuplicates() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple", 1),
				ref("refs/heads/banana", 2));
		List<Ref> delta2 = Arrays.asList(
				ref("refs/heads/apple", 3),
				ref("refs/heads/apple", 4));

		MergedReftable mr = merge(write(delta1, 1000), write(delta2, 2000));
		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/apple", rc.getRef().getName());
			assertEquals(id(3), rc.getRef().getObjectId());
			assertEquals(2000, rc.getRef().getUpdateIndex());
			assertTrue(rc.next());
			assertEquals("refs/heads/banana", rc.getRef().getName());
			assertEquals(id(2), rc.getRef().getObjectId());
			assertEquals(1000, rc.getRef().getUpdateIndex());
			assertFalse(rc.next());
		}
	}


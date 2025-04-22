	@Test
	public void twoTableSeekPast() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(ref("refs/heads/banana"

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.seekPastRef("refs/heads/apple")) {
			assertTrue(rc.next());
			assertEquals("refs/heads/banana"
			assertEquals(id(3)
			assertTrue(rc.next());
			assertEquals("refs/heads/master"
			assertEquals(id(2)
			assertEquals(1
		}
	}


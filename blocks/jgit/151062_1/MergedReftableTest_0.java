	@Test
	public void tableByIDDeletion() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(ref("refs/heads/master"

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.byObjectId(id(2))) {
			assertFalse(rc.next());
		}
	}


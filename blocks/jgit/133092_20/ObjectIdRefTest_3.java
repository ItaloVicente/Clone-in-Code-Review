	@Test
	public void testUpdateIndex() {
		ObjectIdRef r;

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertTrue(r.getUpdateIndex() == 3);

		r = new ObjectIdRef.PeeledTag(Ref.Storage.LOOSE
		assertTrue(r.getUpdateIndex() == 4);

		r = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
		assertTrue(r.getUpdateIndex() == 5);
	}

	@Test
	public void testUpdateIndexNotSet() {
		List<ObjectIdRef> r = Arrays.asList(
				new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
				new ObjectIdRef.PeeledTag(Ref.Storage.LOOSE
				new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE

		for (ObjectIdRef ref : r) {
			try {
				ref.getUpdateIndex();
				fail("Update index wasn't set. It must throw");
			} catch (UnsupportedOperationException u) {
			}
		}
	}



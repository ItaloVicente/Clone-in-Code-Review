	public void testIterator_MissingUnresolvedSymbolicRefIsBug() {
		final Ref master = newRef("refs/heads/master"
		final Ref headR = newRef("HEAD"

		loose = toList(master);
		resolved = toList(headR);

		RefMap map = new RefMap(""
		Iterator<Ref> itr = map.values().iterator();
		try {
			itr.hasNext();
			fail("iterator did not catch bad input");
		} catch (IllegalStateException err) {
		}
	}

	public void testMerge_HeadMaster() {
		final Ref master = newRef("refs/heads/master"
		final Ref headU = newRef("HEAD"
		final Ref headR = newRef("HEAD"

		loose = toList(headU
		resolved = toList(headR);

		RefMap map = new RefMap(""
		assertEquals(2
		assertFalse(map.isEmpty());
		assertTrue(map.containsKey("refs/heads/master"));
		assertSame(master

		assertSame(headR

		Iterator<Ref> itr = map.values().iterator();
		assertTrue(itr.hasNext());
		assertSame(headR
		assertTrue(itr.hasNext());
		assertSame(master
		assertFalse(itr.hasNext());
	}


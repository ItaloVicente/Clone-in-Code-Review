	public void testPut_CollapseResolved() {
		final Ref master = newRef("refs/heads/master"
		final Ref headU = newRef("HEAD"
		final Ref headR = newRef("HEAD"
		final Ref a = newRef("refs/heads/A"

		loose = toList(headU
		resolved = toList(headR);

		RefMap map = new RefMap(""
		assertNull(map.put(a.getName()
		assertSame(a
		assertSame(headR
	}

	public void testRemove() {
		final Ref master = newRef("refs/heads/master"
		final Ref headU = newRef("HEAD"
		final Ref headR = newRef("HEAD"

		packed = toList(master);
		loose = toList(headU
		resolved = toList(headR);

		RefMap map = new RefMap(""
		assertNull(map.remove("not.a.reference"));

		assertSame(master
		assertNull(map.get("refs/heads/master"));

		assertSame(headR
		assertNull(map.get("HEAD"));

		assertTrue(map.isEmpty());
	}


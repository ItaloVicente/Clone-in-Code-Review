
	@Test
	public void testUpdateWithSymRefsFillInHead() {
		final String oidName = "0000000000000000000000000000000000000001";
		final Ref advertised = new ObjectIdRef.PeeledNonTag(Ref.Storage.NETWORK
				Constants.HEAD

		final Map<String
		refMap.put(advertised.getName()

		final Map<String
		symRefs.put("HEAD"

		BasePackConnection.updateWithSymRefs(refMap

		assertThat(refMap
		assertThat(refMap
		final Ref headRef = refMap.get("HEAD");
		final Ref mainRef = refMap.get("refs/heads/main");
		assertThat(headRef
		final SymbolicRef headSymRef = (SymbolicRef) headRef;
		assertEquals(Constants.HEAD
		assertSame(mainRef
		assertEquals(oidName
		assertEquals(oidName
	}

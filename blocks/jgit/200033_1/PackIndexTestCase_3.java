	@Test
	public void testEntriesPositionsRamdomAccess() {
		assertEquals(4
				.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7")));
		assertEquals(7
				.fromString("c59759f143fb1fe21c197981df75a7ee00290799")));
		assertEquals(0
				.fromString("4b825dc642cb6eb9a060e54bf8d69288fbee4904")));
	}

	@Test
	public void testEntriesPositionsWithIteratorOrder() {
		int i = 0;
		for (MutableEntry me : smallIdx) {
			assertEquals(smallIdx.findPosition(me.toObjectId())
			i++;
		}
		i = 0;
		for (MutableEntry me : denseIdx) {
			assertEquals(denseIdx.findPosition(me.toObjectId())
			i++;
		}
	}

	@Test
	public void testEntriesPositionsObjectNotInPack() {
		assertEquals(-1
		assertEquals(-1
				.fromString("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")));
	}


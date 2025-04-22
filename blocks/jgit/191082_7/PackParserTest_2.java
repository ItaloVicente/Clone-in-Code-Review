	@Test
	public void testParseOfsDeltaFullSize() throws Exception {
		final byte[] data = Constants.encode("0123456789");
		try (TestRepository<Repository> d = new TestRepository<>(db)) {
			db.incrementOpen();
			assertTrue(db.getObjectDatabase().has(d.blob(data)));
		}

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack
		deflate(pack
		pack.write(19);
		deflate(pack
		digest(pack);

		PackParser p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.parse(NullProgressMonitor.INSTANCE);

		List<PackedObjectInfo> sortedObjectList = p.getSortedObjectList(null);
		assertEquals(sortedObjectList.size()

		PackedObjectInfo deltifiedObj = sortedObjectList.get(0);
		assertEquals(deltifiedObj.getName()
				"16646543f87fb53e30b032eec7dfc88f2e717966");
		assertEquals(deltifiedObj.getOffset()
		assertEquals(deltifiedObj.getType()
		assertEquals(deltifiedObj.getFullSize()

		PackedObjectInfo baseObj = sortedObjectList.get(1);
		assertEquals(baseObj.getName()
				"ad471007bd7f5983d273b9584e5629230150fd54");
		assertEquals(baseObj.getOffset()
		assertEquals(baseObj.getType()
		assertEquals(baseObj.getFullSize()
	}


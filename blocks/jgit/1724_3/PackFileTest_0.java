	public void testDelta_LargeObjectChain_HasReverseSeek() throws Exception {
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		byte[] data0 = new byte[4 * streamThreshold + 5];
		Arrays.fill(data0
		ObjectId id0 = fmt.idFor(Constants.OBJ_BLOB

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
		packHeader(pack
		objectHeader(pack
		deflate(pack

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		DeltaEncoder de = new DeltaEncoder(tmp
		de.copy(2 * streamThreshold
		de.copy(1 * streamThreshold
		de.copy(0 * streamThreshold
		de.insert("b");
		de.copy(data0.length - 5
		byte[] delta1 = tmp.toByteArray();
		byte[] data1 = BinaryDelta.apply(data0
		ObjectId id1 = fmt.idFor(Constants.OBJ_BLOB
		objectHeader(pack
		id0.copyRawTo(pack);
		deflate(pack

		digest(pack);
		final byte[] raw = pack.toByteArray();
		IndexPack ip = IndexPack.create(repo
		ip.setFixThin(true);
		ip.index(NullProgressMonitor.INSTANCE);
		ip.renameAndOpenPack();

		assertTrue("has blob"

		ObjectLoader ol = wc.open(id1);
		assertNotNull("created loader"
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data1.length
		assertTrue("is large"
		try {
			ol.getCachedBytes();
			fail("Should have thrown LargeObjectException");
		} catch (LargeObjectException tooBig) {
			assertEquals(MessageFormat.format(
					JGitText.get().largeObjectException
					tooBig.getMessage());
		}

		ObjectStream in = ol.openStream();
		assertNotNull("have stream"
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data1.length
		byte[] act = new byte[data1.length];
		IO.readFully(in
		assertTrue("same content"
		assertEquals("stream at EOF"
		in.close();
	}


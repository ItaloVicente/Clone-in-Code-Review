	public void testDelta_LargeInstructionStream() throws Exception {
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		byte[] data0 = new byte[32];
		Arrays.fill(data0, (byte) 0xf3);
		ObjectId id0 = fmt.idFor(Constants.OBJ_BLOB, data0);

		byte[] data3 = rng.nextBytes(ObjectLoader.STREAM_THRESHOLD + 5);
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		DeltaEncoder de = new DeltaEncoder(tmp, data0.length, data3.length);
		de.insert(data3, 0, data3.length);
		byte[] delta3 = tmp.toByteArray();
		assertTrue(delta3.length > ObjectLoader.STREAM_THRESHOLD);

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(
				ObjectLoader.STREAM_THRESHOLD + 1024);
		packHeader(pack, 2);
		objectHeader(pack, Constants.OBJ_BLOB, data0.length);
		deflate(pack, data0);

		ObjectId id3 = fmt.idFor(Constants.OBJ_BLOB, data3);
		objectHeader(pack, Constants.OBJ_REF_DELTA, delta3.length);
		id0.copyRawTo(pack);
		deflate(pack, delta3);

		digest(pack);
		final byte[] raw = pack.toByteArray();
		IndexPack ip = IndexPack.create(repo, new ByteArrayInputStream(raw));
		ip.setFixThin(true);
		ip.index(NullProgressMonitor.INSTANCE);
		ip.renameAndOpenPack();

		assertTrue("has blob", wc.has(id3));

		ObjectLoader ol = wc.open(id3);
		assertNotNull("created loader", ol);
		assertEquals(Constants.OBJ_BLOB, ol.getType());
		assertEquals(data3.length, ol.getSize());
		assertTrue("is large", ol.isLarge());
		try {
			ol.getCachedBytes();
			fail("Should have thrown LargeObjectException");
		} catch (LargeObjectException tooBig) {
			assertEquals(MessageFormat.format(
					JGitText.get().largeObjectException, id3.name()), tooBig
					.getMessage());
		}

		ObjectStream in = ol.openStream();
		assertNotNull("have stream", in);
		assertEquals(Constants.OBJ_BLOB, in.getType());
		assertEquals(data3.length, in.getSize());
		byte[] act = new byte[data3.length];
		IO.readFully(in, act, 0, data3.length);
		assertTrue("same content", Arrays.equals(act, data3));
		assertEquals("stream at EOF", -1, in.read());
		in.close();
	}


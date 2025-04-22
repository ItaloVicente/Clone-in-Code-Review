		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		byte[] data0 = new byte[512];
		Arrays.fill(data0, (byte) 0xf3);
		ObjectId id0 = fmt.idFor(Constants.OBJ_BLOB, data0);

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
		packHeader(pack, 4);
		objectHeader(pack, Constants.OBJ_BLOB, data0.length);
		deflate(pack, data0);

		byte[] data1 = clone(0x01, data0);
		byte[] delta1 = delta(data0, data1);
		ObjectId id1 = fmt.idFor(Constants.OBJ_BLOB, data1);
		objectHeader(pack, Constants.OBJ_REF_DELTA, delta1.length);
		id0.copyRawTo(pack);
		deflate(pack, delta1);

		byte[] data2 = clone(0x02, data1);
		byte[] delta2 = delta(data1, data2);
		ObjectId id2 = fmt.idFor(Constants.OBJ_BLOB, data2);
		objectHeader(pack, Constants.OBJ_REF_DELTA, delta2.length);
		id1.copyRawTo(pack);
		deflate(pack, delta2);

		byte[] data3 = clone(0x03, data2);
		byte[] delta3 = delta(data2, data3);
		ObjectId id3 = fmt.idFor(Constants.OBJ_BLOB, data3);
		objectHeader(pack, Constants.OBJ_REF_DELTA, delta3.length);
		id2.copyRawTo(pack);
		deflate(pack, delta3);

		digest(pack);
		PackParser ip = index(pack.toByteArray());
		ip.setAllowThin(true);
		ip.parse(NullProgressMonitor.INSTANCE);

		assertTrue("has blob", wc.has(id3));

		ObjectLoader ol = wc.open(id3);
		assertNotNull("created loader", ol);
		assertEquals(Constants.OBJ_BLOB, ol.getType());
		assertEquals(data3.length, ol.getSize());
		assertFalse("is large", ol.isLarge());
		assertNotNull(ol.getCachedBytes());
		assertArrayEquals(data3, ol.getCachedBytes());

		ObjectStream in = ol.openStream();
		assertNotNull("have stream", in);
		assertEquals(Constants.OBJ_BLOB, in.getType());
		assertEquals(data3.length, in.getSize());
		byte[] act = new byte[data3.length];
		IO.readFully(in, act, 0, data3.length);
		assertTrue("same content", Arrays.equals(act, data3));
		assertEquals("stream at EOF", -1, in.read());
		in.close();

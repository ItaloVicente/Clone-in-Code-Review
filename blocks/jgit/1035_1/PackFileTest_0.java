
	public void testDelta_SmallObjectChain() throws Exception {
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		byte[] data0 = new byte[512];
		Arrays.fill(data0
		ObjectId id0 = fmt.idFor(Constants.OBJ_BLOB

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
		packHeader(pack
		objectHeader(pack
		deflate(pack

		byte[] data1 = clone(0x01
		byte[] delta1 = delta(data0
		ObjectId id1 = fmt.idFor(Constants.OBJ_BLOB
		objectHeader(pack
		id0.copyRawTo(pack);
		deflate(pack

		byte[] data2 = clone(0x02
		byte[] delta2 = delta(data1
		ObjectId id2 = fmt.idFor(Constants.OBJ_BLOB
		objectHeader(pack
		id1.copyRawTo(pack);
		deflate(pack

		byte[] data3 = clone(0x03
		byte[] delta3 = delta(data2
		ObjectId id3 = fmt.idFor(Constants.OBJ_BLOB
		objectHeader(pack
		id2.copyRawTo(pack);
		deflate(pack

		digest(pack);
		final byte[] raw = pack.toByteArray();
		IndexPack ip = IndexPack.create(repo
		ip.setFixThin(true);
		ip.index(NullProgressMonitor.INSTANCE);
		ip.renameAndOpenPack();

		assertTrue("has blob"

		ObjectLoader ol = wc.open(id3);
		assertNotNull("created loader"
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data3.length
		assertFalse("is large"
		assertNotNull(ol.getCachedBytes());
		assertTrue(Arrays.equals(data3

		ObjectStream in = ol.openStream();
		assertNotNull("have stream"
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data3.length
		byte[] act = new byte[data3.length];
		IO.readFully(in
		assertTrue("same content"
		assertEquals("stream at EOF"
		in.close();
	}

	public void testDelta_LargeObjectChain() throws Exception {
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		byte[] data0 = new byte[UnpackedObject.LARGE_OBJECT + 5];
		Arrays.fill(data0
		ObjectId id0 = fmt.idFor(Constants.OBJ_BLOB

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
		packHeader(pack
		objectHeader(pack
		deflate(pack

		byte[] data1 = clone(0x01
		byte[] delta1 = delta(data0
		ObjectId id1 = fmt.idFor(Constants.OBJ_BLOB
		objectHeader(pack
		id0.copyRawTo(pack);
		deflate(pack

		byte[] data2 = clone(0x02
		byte[] delta2 = delta(data1
		ObjectId id2 = fmt.idFor(Constants.OBJ_BLOB
		objectHeader(pack
		id1.copyRawTo(pack);
		deflate(pack

		byte[] data3 = clone(0x03
		byte[] delta3 = delta(data2
		ObjectId id3 = fmt.idFor(Constants.OBJ_BLOB
		objectHeader(pack
		id2.copyRawTo(pack);
		deflate(pack

		digest(pack);
		final byte[] raw = pack.toByteArray();
		IndexPack ip = IndexPack.create(repo
		ip.setFixThin(true);
		ip.index(NullProgressMonitor.INSTANCE);
		ip.renameAndOpenPack();

		assertTrue("has blob"

		ObjectLoader ol = wc.open(id3);
		assertNotNull("created loader"
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data3.length
		assertTrue("is large"
		try {
			ol.getCachedBytes();
			fail("Should have thrown LargeObjectException");
		} catch (LargeObjectException tooBig) {
			assertEquals(id3.name()
		}

		ObjectStream in = ol.openStream();
		assertNotNull("have stream"
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data3.length
		byte[] act = new byte[data3.length];
		IO.readFully(in
		assertTrue("same content"
		assertEquals("stream at EOF"
		in.close();
	}

	public void testDelta_LargeInstructionStream() throws Exception {
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		byte[] data0 = new byte[32];
		Arrays.fill(data0
		ObjectId id0 = fmt.idFor(Constants.OBJ_BLOB

		byte[] data3 = rng.nextBytes(UnpackedObject.LARGE_OBJECT + 5);
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		DeltaEncoder de = new DeltaEncoder(tmp
		de.insert(data3
		byte[] delta3 = tmp.toByteArray();
		assertTrue(delta3.length > UnpackedObject.LARGE_OBJECT);

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
		packHeader(pack
		objectHeader(pack
		deflate(pack

		ObjectId id3 = fmt.idFor(Constants.OBJ_BLOB
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

		ObjectLoader ol = wc.open(id3);
		assertNotNull("created loader"
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data3.length
		assertTrue("is large"
		try {
			ol.getCachedBytes();
			fail("Should have thrown LargeObjectException");
		} catch (LargeObjectException tooBig) {
			assertEquals(id3.name()
		}

		ObjectStream in = ol.openStream();
		assertNotNull("have stream"
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data3.length
		byte[] act = new byte[data3.length];
		IO.readFully(in
		assertTrue("same content"
		assertEquals("stream at EOF"
		in.close();
	}

	private byte[] clone(int first
		byte[] r = new byte[base.length];
		System.arraycopy(base
		r[0] = (byte) first;
		return r;
	}

	private byte[] delta(byte[] base
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		DeltaEncoder de = new DeltaEncoder(tmp
		de.insert(dest
		de.copy(1
		return tmp.toByteArray();
	}

	private void packHeader(TemporaryBuffer.Heap pack
			throws IOException {
		final byte[] hdr = new byte[8];
		NB.encodeInt32(hdr
		NB.encodeInt32(hdr
		pack.write(Constants.PACK_SIGNATURE);
		pack.write(hdr
	}

	private void objectHeader(TemporaryBuffer.Heap pack
			throws IOException {
		byte[] buf = new byte[8];
		int nextLength = sz >>> 4;
		buf[0] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (sz & 0x0F));
		sz = nextLength;
		int n = 1;
		while (sz > 0) {
			nextLength >>>= 7;
			buf[n++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (sz & 0x7F));
			sz = nextLength;
		}
		pack.write(buf
	}

	private void deflate(TemporaryBuffer.Heap pack
			throws IOException {
		final Deflater deflater = new Deflater();
		final byte[] buf = new byte[128];
		deflater.setInput(content
		deflater.finish();
		do {
			final int n = deflater.deflate(buf
			if (n > 0)
				pack.write(buf
		} while (!deflater.finished());
		deflater.end();
	}

	private void digest(TemporaryBuffer.Heap buf) throws IOException {
		MessageDigest md = Constants.newMessageDigest();
		md.update(buf.toByteArray());
		buf.write(md.digest());
	}

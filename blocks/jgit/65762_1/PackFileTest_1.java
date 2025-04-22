		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
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
			PackParser ip = index(pack.toByteArray());
			ip.setAllowThin(true);
			ip.parse(NullProgressMonitor.INSTANCE);

			assertTrue("has blob"

			ObjectLoader ol = wc.open(id3);
			assertNotNull("created loader"
			assertEquals(Constants.OBJ_BLOB
			assertEquals(data3.length
			assertFalse("is large"
			assertNotNull(ol.getCachedBytes());
			assertArrayEquals(data3

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

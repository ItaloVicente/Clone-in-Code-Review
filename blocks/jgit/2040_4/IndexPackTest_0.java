	public void testPackWithDuplicateBlob() throws Exception {
		final byte[] data = Constants.encode("0123456789abcdefg");
		TestRepository<Repository> d = new TestRepository<Repository>(db);
		assertTrue(db.hasObject(d.blob(data)));

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack
		pack.write((Constants.OBJ_BLOB) << 4 | 0x80 | 1);
		pack.write(1);
		deflate(pack
		digest(pack);

		final byte[] raw = pack.toByteArray();
		IndexPack ip = IndexPack.create(db
		ip.setStreamFileThreshold(1);
		ip.index(NullProgressMonitor.INSTANCE);
		ip.renameAndOpenPack();
	}

